package shop.jnjeaaaat.easyrsv.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shop.jnjeaaaat.easyrsv.domain.dto.review.ReviewDto;
import shop.jnjeaaaat.easyrsv.domain.dto.review.ReviewInputRequest;
import shop.jnjeaaaat.easyrsv.domain.dto.review.ReviewInputResponse;
import shop.jnjeaaaat.easyrsv.domain.model.Reservation;
import shop.jnjeaaaat.easyrsv.domain.model.Review;
import shop.jnjeaaaat.easyrsv.domain.model.Shop;
import shop.jnjeaaaat.easyrsv.domain.model.User;
import shop.jnjeaaaat.easyrsv.domain.repository.ReservationRepository;
import shop.jnjeaaaat.easyrsv.domain.repository.ReviewRepository;
import shop.jnjeaaaat.easyrsv.domain.repository.ShopRepository;
import shop.jnjeaaaat.easyrsv.domain.repository.UserRepository;
import shop.jnjeaaaat.easyrsv.exception.BaseException;
import shop.jnjeaaaat.easyrsv.service.ReviewService;
import shop.jnjeaaaat.easyrsv.utils.JwtTokenProvider;

import java.time.LocalDateTime;
import java.util.Objects;

import static shop.jnjeaaaat.easyrsv.domain.dto.base.BaseResponseStatus.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class ReviewServiceImpl implements ReviewService {

    private final ReviewRepository reviewRepository;
    private final ReservationRepository reservationRepository;
    private final UserRepository userRepository;
    private final ShopRepository shopRepository;

    private final JwtTokenProvider jwtTokenProvider;

    /*
    예약 번호, 내용 request
    해당 예약 번호에 대해, 새로운 리뷰 작성
     */
    @Override
    @Transactional
    public ReviewInputResponse createReview(ReviewInputRequest request) {
        Long userId = jwtTokenProvider.getUserIdFromToken();
        log.info("[createReview] 리뷰 작성 - 유저 id : {}", userId);

        // Reservation Entity
        Reservation reservation = reservationRepository.findById(request.getReservationId())
                .orElseThrow(() -> new BaseException(RESERVATION_NOT_FOUND));
        // User Entity
        User user = userRepository.findById(reservation.getUser().getId())
                .orElseThrow(() -> new BaseException(USER_NOT_FOUND));
        // Shop Entity
        Shop shop = shopRepository.findById(reservation.getShop().getId())
                .orElseThrow(() -> new BaseException(SHOP_NOT_FOUND));

        // 유저가 다를 때
        if (!Objects.equals(userId, user.getId())) {
            throw new BaseException(USER_UN_MATCH);
        }
        // 이미 해당 예약에 대해 작성했을때
        if (reviewRepository.findByReservation(reservation).isPresent()) {
            throw new BaseException(ALREADY_WRITE_REVIEW);
        }
        // 아직 예약 시간도 안됐을때
        if (LocalDateTime.now().isBefore(reservation.getReservationDate())) {
            throw new BaseException(TOO_EARLY_REVIEW);
        }

        // 리뷰까지 쓰면 예약 완료
        reservation.setFinished(true);
        reservation.setUpdatedAt(LocalDateTime.now());

        return ReviewInputResponse.from(
                ReviewDto.from(reviewRepository.save(
                                Review.builder()
                                        .user(user)
                                        .shop(shop)
                                        .reservation(reservation)
                                        .description(request.getDescription())
                                        .build()
                        )
                )
        );
    }
}
