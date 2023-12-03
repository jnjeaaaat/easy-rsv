package shop.jnjeaaaat.easyrsv.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
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

import java.util.Objects;

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
        // todo: 해당 상점에 이미 예약한 유저일 때

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

        // 예약한 사람 id 와 토큰 userId 값이 다를 때
        if (!Objects.equals(userId, reservation.getUser().getId())) {
            throw new BaseException(USER_UN_MATCH);
        }

        // 예약 취소
        reservation.setShop(null);  // Shop 정보 먼저 null 로 변경
        reservation.setUser(null);  // User 정보 먼저 null 로 변경
        reservationRepository.deleteById(reservationId);
        log.info("[cancelReservation] 예약 취소 성공");

    }
}
