package shop.jnjeaaaat.easyrsv.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shop.jnjeaaaat.easyrsv.domain.dto.review.*;
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
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

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

    /*
    리뷰 id, 변경할 내용 받아서 내용 수정
    createReview 와 같은 form return
     */
    @Override
    @Transactional
    public ReviewInputResponse modifyReview(Long reviewId, ReviewModifyRequest request) {
        log.info("[modifyReview] 리뷰 수정 - 수정할 리뷰 id : {}", reviewId);
        Long userId = jwtTokenProvider.getUserIdFromToken();

        // Review Entity
        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new BaseException(REVIEW_NOT_FOUND));
        // Shop Entity
        shopRepository.findById(review.getShop().getId())
                .orElseThrow(() -> new BaseException(SHOP_NOT_FOUND));

        // 리뷰를 쓴 유저와 요청한 유저가 다를 때
        if (!Objects.equals(userId, review.getUser().getId())) {
            throw new BaseException(USER_UN_MATCH);
        }

        // 내용이 변경되면 데이터 변경
        if (!review.getDescription().equals(request.getDescription())) {
            // 리뷰 내용 수정
            review.setDescription(request.getDescription());
            // 수정된 리뷰 날짜
            review.setUpdatedAt(LocalDateTime.now());
        }

        return ReviewInputResponse.from(
                ReviewDto.from(review)
        );
    }

    /*
    해당 상점에 리뷰들 조회
    상점 id 값 받아서
    해당 상점의 리뷰 리스트 return

    리뷰 id, 리뷰 작성 유저, 리뷰 작성된 상점 id, 리뷰 내용 Response
     */
    @Override
    public List<ReviewShopResponse> getShopReviews(Long shopId) {
        log.info("[getShopReviews] 상점 리뷰 리스트 조회 - 상점 id : {}", shopId);

        // Shop Entity
        Shop shop = shopRepository.findById(shopId)
                .orElseThrow(() -> new BaseException(SHOP_NOT_FOUND));

        // Review Entity List
        List<Review> reviewList = reviewRepository.findAllByShop(shop);

        // ReviewDto List
        List<ReviewDto> reviewDtoList = reviewList
                .stream()
                .map(ReviewDto::from)
                .collect(Collectors.toList());

        // ReviewShopResponse List 로 변환 후 return
        return reviewDtoList.stream()
                .map(ReviewShopResponse::from)
                .collect(Collectors.toList());
    }

    /*
    해당 리뷰 조회
    리뷰 id 값 받아서 해당 리뷰 자세한 정보 조회
     */
    @Override
    public ReviewDto getReview(Long reviewId) {

        log.info("[getReview] 리뷰 조회 - 리뷰 id : {}", reviewId);

        // Review Entity
        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new BaseException(REVIEW_NOT_FOUND));

        // User Entity 존재 유무 체크
        userRepository.findById(review.getUser().getId())
                .orElseThrow(() -> new BaseException(USER_NOT_FOUND));
        // Shop Entity 존재 유무 체크
        shopRepository.findById(review.getShop().getId())
                .orElseThrow(() -> new BaseException(SHOP_NOT_FOUND));

        return ReviewDto.from(review);
    }

    /*
    리뷰 id 값으로 해당 리뷰 삭제
    상점 주인, 작성자만 삭제 가능
     */
    @Override
    @Transactional
    public void deleteReview(Long reviewId) {
        Long userId = jwtTokenProvider.getUserIdFromToken();
        log.info("[deleteReview] 리뷰 삭제 - 삭제하려는 유저 id : {}, 삭제하려는 리뷰 id : {}", userId, reviewId);

        // Review Entity
        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new BaseException(REVIEW_NOT_FOUND));
        // User Entity 존재 유무 확인
        userRepository.findById(review.getUser().getId())
                .orElseThrow(() -> new BaseException(USER_NOT_FOUND));
        // Shop Entity 존재 유무 확인
        shopRepository.findById(review.getShop().getId())
                .orElseThrow(() -> new BaseException(SHOP_NOT_FOUND));

        // 작성한 본인이 아니거나, 상점 주인이 아니라면
        boolean notMe = !Objects.equals(userId, review.getUser().getId());
        boolean notMyShop = !Objects.equals(userId, review.getShop().getOwner().getId());
        if (notMe && notMyShop) {
            throw new BaseException(NO_AUTH_TO_BROWSE);
        }
        // 누가 삭제하려는건지 확인하기 위한 log
        if (!notMe) {
            log.info("[deleteReview] 작성자가 삭제 요청 - 유저 email : {}", review.getUser().getEmail());
        }
        if (!notMyShop) {
            log.info("[deleteReview] 상점 주인이 삭제 요청 - 유저 email : {}", review.getShop().getOwner().getEmail());
        }

        // 리뷰 삭제
        review.setShop(null);  // Shop 정보 먼저 null 로 변경
        review.setUser(null);  // User 정보 먼저 null 로 변경
        review.setReservation(null);  // Reservation 정보 먼저 null 로 변경
        reviewRepository.deleteById(reviewId);
        log.info("[deleteReview] 리뷰 삭제 성공");
    }
}
