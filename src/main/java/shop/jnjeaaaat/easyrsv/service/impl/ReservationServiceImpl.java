package shop.jnjeaaaat.easyrsv.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shop.jnjeaaaat.easyrsv.domain.dto.reservation.ReservationDto;
import shop.jnjeaaaat.easyrsv.domain.dto.reservation.ReservationInputRequest;
import shop.jnjeaaaat.easyrsv.domain.dto.reservation.ReservationInputResponse;
import shop.jnjeaaaat.easyrsv.domain.model.Reservation;
import shop.jnjeaaaat.easyrsv.domain.model.Shop;
import shop.jnjeaaaat.easyrsv.domain.model.User;
import shop.jnjeaaaat.easyrsv.domain.repository.ReservationRepository;
import shop.jnjeaaaat.easyrsv.domain.repository.ShopRepository;
import shop.jnjeaaaat.easyrsv.domain.repository.UserRepository;
import shop.jnjeaaaat.easyrsv.exception.BaseException;
import shop.jnjeaaaat.easyrsv.service.ReservationService;
import shop.jnjeaaaat.easyrsv.utils.JwtTokenProvider;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static shop.jnjeaaaat.easyrsv.domain.dto.base.BaseResponseStatus.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class ReservationServiceImpl implements ReservationService {

    private final JwtTokenProvider jwtTokenProvider;
    private final ReservationRepository reservationRepository;
    private final ShopRepository shopRepository;
    private final UserRepository userRepository;

    /*
    상점 예약 등록
    유저 id, 상점 id, 예약 날짜 받아서 상점 예약
     */
    @Override
    public ReservationInputResponse reserveShop(ReservationInputRequest request) {

        // 토큰으로부터 userId 받아오기
        Long userId = jwtTokenProvider.getUserIdFromToken();
        log.info("[reserveShop] 상점 예약 -" +
                " 예약한 유저 id : {}, 예약한 상점 id : {}", userId, request.getShopId());
        // Shop 존재 유무 체크
        Shop shop = shopRepository.findById(request.getShopId())
                .orElseThrow(() -> new BaseException(SHOP_NOT_FOUND));
        // User 존재 유무 체크
        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new BaseException(USER_NOT_FOUND));

        // 예약한 사람과 token userId 가 다를 때
        if (!Objects.equals(userId, user.getId())) {
            throw new BaseException(USER_UN_MATCH);
        }
        // 예약한 사람이 상점 주인일 때
        if (Objects.equals(request.getUserId(), shop.getOwner().getId())) {
            throw new BaseException(OWNER_CANT_RESERVE);
        }
        // 해당 상점에 이미 예약한 유저일 때
        if (reservationRepository.findByUserAndShop(user, shop).isPresent()) {
            throw new BaseException(ALREADY_RESERVATED);
        }

        ReservationDto reservationDto =
                ReservationDto.from(reservationRepository.save(
                                Reservation.builder()
                                        .user(user)
                                        .shop(shop)
                                        .reservationDate(request.getReservationDate())
                                        .isApproved(false) // 처음 승인 여부는 false 로 고정값
                                        .build()
                        )
                );

        return ReservationInputResponse.from(reservationDto);
    }

    /*
    예약 날짜 변경
    해당 예약 id, userId, shopId, reservationDate 값 받아서
    reservationDate 값만 변경 가능
     */
    @Override
    @Transactional
    public ReservationInputResponse modifyReservation(Long reservationId, ReservationInputRequest request) {
        log.info("[modifyReservation] 예약 취소 - id : {}", reservationId);
        Long userId = jwtTokenProvider.getUserIdFromToken();

        // 예약 정보 받아오기
        Reservation reservation = reservationRepository.findById(reservationId)
                .orElseThrow(() -> new BaseException(RESERVATION_NOT_FOUND));

        // token 유저 id 와 해당 예약의 유저 id 값이 다를 때
        if (!Objects.equals(userId, reservation.getUser().getId())) {
            throw new BaseException(USER_UN_MATCH);
        }
        // 예약한 본인만 변경 가능
        if (!Objects.equals(userId, request.getUserId())) {
            throw new BaseException(MODIFY_JUST_ME);
        }
        // 예약한 상점이 맞는지 확인
        if (!Objects.equals(request.getShopId(), reservation.getShop().getId())) {
            throw new BaseException(MODIFY_JUST_THAT_SHOP);
        }

        // 날짜 변경
        reservation.setReservationDate(request.getReservationDate());
        // 수정된 날짜 변경
        reservation.setUpdatedAt(LocalDateTime.now());

        return ReservationInputResponse.from(
                ReservationDto.from(reservation)
        );
    }

    /*
    유저 id 값 받아서
    본인이 예약한 예약 리스트 조회
     */
    @Override
    public List<ReservationDto> getMyReservations(Long userId) {
        Long tokenUserId = jwtTokenProvider.getUserIdFromToken();
        log.info("[getMyReservations] 유저의 예약 리스트 조회 - 유저 id : {}", tokenUserId);

        // 토큰 유저와 요청한 유저가 다를 때
        if (!Objects.equals(tokenUserId, userId)) {
            throw new BaseException(USER_UN_MATCH);
        }

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new BaseException(USER_NOT_FOUND));

        return reservationRepository.findAllByUser(user) // Reservation Entity List
                .stream() // Stream 으로
                .map(ReservationDto::from)  // ReservationDto 의 from()
                .collect(Collectors.toList());  // List 로 반환
    }

    /*
    상점 id 값 받아서
    본인 상점에 대한 예약 리스트 조회
     */
    @Override
    public List<ReservationDto> getMyShopReservations(Long shopId) {
        log.info("[getMyShopReservations] 해당 상점의 예약 리스트 조회 - 상점 id : {}", shopId);
        Long userId = jwtTokenProvider.getUserIdFromToken();

        // 해당 상점
        Shop shop = shopRepository.findById(shopId)
                .orElseThrow(() -> new BaseException(SHOP_NOT_FOUND));

        // 해당 상점의 주인이 아닐 때
        if (!Objects.equals(userId, shop.getOwner().getId())) {
            throw new BaseException(NOT_OWNER);
        }

        return reservationRepository.findAllByShop(shop)
                .stream()
                .map(ReservationDto::from)
                .collect(Collectors.toList());
    }

    /*
    예약 정보 조회
    예약 id 값으로 조회
     */
    @Override
    public ReservationDto getReservation(Long reservationId) {
        log.info("[getReservation] 예약 조회 - 예약 id : {}", reservationId);
        Long userId = jwtTokenProvider.getUserIdFromToken();

        // Reservation Entity
        Reservation reservation = reservationRepository.findById(reservationId)
                .orElseThrow(() -> new BaseException(RESERVATION_NOT_FOUND));

        // 예약한 본인이 아니거나, 상점 주인이 아니라면
        boolean notMe = !Objects.equals(userId, reservation.getUser().getId());
        boolean notMyShop = !Objects.equals(userId, reservation.getShop().getOwner().getId());
        if (notMe && notMyShop) {
            throw new BaseException(NO_AUTH_TO_BROWSE);
        }

        // 누가 조회한건지 확인하기 위한 log
        if (!notMe) {
            log.info("[getReservation] 예약자 본인이 조회 확인 - 유저 email : {}", reservation.getUser().getEmail());
        }
        if (!notMyShop) {
            log.info("[getReservation] 상점 주인이 조회 확인 - 유저 email : {}", reservation.getShop().getOwner().getEmail());
        }

        return ReservationDto.from(reservation);
    }

    /*
    예약 취소
    예약 번호 id 값 받아서 해당 예약 삭제

    삭제 전에 영속성 오류 방지를 위해
    Shop, User 정보 null 로 변경
     */
    @Override
    public void cancelReservation(Long reservationId) {

        // 토큰으로부터 userId 받아오기
        Long userId = jwtTokenProvider.getUserIdFromToken();
        log.info("[cancelReservation] 예약 취소");

        // 해당 예약 받아오기, 없다면 exception
        Reservation reservation = reservationRepository.findById(reservationId)
                .orElseThrow(() -> new BaseException(RESERVATION_NOT_FOUND));

        // 예약한 본인이 아니거나, 상점 주인이 아니라면
        boolean notMe = !Objects.equals(userId, reservation.getUser().getId());
        boolean notMyShop = !Objects.equals(userId, reservation.getShop().getOwner().getId());
        if (notMe && notMyShop) {
            throw new BaseException(NO_AUTH_TO_BROWSE);
        }
        // 누가 조회한건지 확인하기 위한 log
        if (!notMe) {
            log.info("[getReservation] 예약자 본인이 조회 확인 - 유저 email : {}", reservation.getUser().getEmail());
        }
        if (!notMyShop) {
            log.info("[getReservation] 상점 주인이 조회 확인 - 유저 email : {}", reservation.getShop().getOwner().getEmail());
        }

        // 예약 취소
        reservation.setShop(null);  // Shop 정보 먼저 null 로 변경
        reservation.setUser(null);  // User 정보 먼저 null 로 변경
        reservationRepository.deleteById(reservationId);
        log.info("[cancelReservation] 예약 취소 성공");

    }

    /*
    예약 id 값 받아서
    해당 상점의 주인이라면 예약을 승인/거부 결정
     */
    @Override
    @Transactional
    public void approveReservation(Long reservationId) {
        log.info("[approveReservation] 예약 승인 - 예약 id : {}", reservationId);
        Long userId = jwtTokenProvider.getUserIdFromToken();

        // Reservation Entity
        Reservation reservation = reservationRepository.findById(reservationId)
                .orElseThrow(() -> new BaseException(RESERVATION_NOT_FOUND));

        // 예약된 상점의 주인이 아닐 때 validation
        if (!Objects.equals(userId, reservation.getShop().getOwner().getId())) {
            throw new BaseException(APPROVE_JUST_OWNER);
        }
        // 이미 승인된 예약일 때 validation
        if (reservation.isApproved()) {
            throw new BaseException(ALREADY_APPROVED);
        }

        // 승인 진행
        reservation.setApproved(true);
        reservation.setUpdatedAt(LocalDateTime.now());
        log.info("[approveReservation] 예약 승인 완료");
    }
}
