package shop.jnjeaaaat.easyrsv.web;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import shop.jnjeaaaat.easyrsv.domain.dto.base.BaseResponse;
import shop.jnjeaaaat.easyrsv.domain.dto.review.*;
import shop.jnjeaaaat.easyrsv.service.ReviewService;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import java.util.List;

import static shop.jnjeaaaat.easyrsv.domain.dto.base.BaseResponseStatus.*;

/**
 * 리뷰 (Review) 관련 api 를 관리하는 Controller
 */
@Slf4j
@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/easy-rsv/v1/review")
public class ReviewController {

    private final ReviewService reviewService;

    /*
    예약 id, 리뷰 내용 Request
    유저 email, 상점 정보, 리뷰 내용, 리뷰 최근 수정 날짜 return
     */
    @PostMapping("")
    public BaseResponse<ReviewInputResponse> createReview(
            @Valid @RequestBody ReviewInputRequest request) {

        log.info("[createReview] 리뷰 작성 요청");

        return new BaseResponse<>(
                SUCCESS_WRITE_REVIEW,
                reviewService.createReview(request)
        );

    }

    /*
    리뷰 id, 수정할 리뷰 내용 Request
    유저 email, 상점 정보, 리뷰 내용, 리뷰 최근 수정 날짜 return
     */
    @PutMapping("/{reviewId}")
    public BaseResponse<ReviewInputResponse> modifyReview(
            @PathVariable @Positive Long reviewId,
            @Valid @RequestBody ReviewModifyRequest request) {

        log.info("[modifyReview] 리뷰 수정 요청");

        return new BaseResponse<>(
                SUCCESS_MODIFY_REVIEW,
                reviewService.modifyReview(reviewId, request)
        );
    }

    /*
    상점 id 값 받아서
    해당 상점의 리뷰 리스트 조회
     */
    @GetMapping("/shop/{shopId}")
    public BaseResponse<List<ReviewShopResponse>> getShopReviews(
            @PathVariable @Positive Long shopId) {

        log.info("[getShopReviews] 상점 리뷰 리스트 요청");

        return new BaseResponse<>(
                SUCCESS_GET_SHOP_REVIEW_LIST,
                reviewService.getShopReviews(shopId)
        );
    }

    /*
    리뷰 id 값 받아서
    해당 리뷰 조회
     */
    @GetMapping("/{reviewId}")
    public BaseResponse<ReviewDto> getReview(
            @PathVariable @Positive Long reviewId) {

        log.info("[getReview] 리뷰 조회 요청");

        return new BaseResponse<>(
                SUCCESS_GET_REVIEW,
                reviewService.getReview(reviewId)
        );
    }

    /*
    리뷰 id 값 받아서
    해당 리뷰 삭제
    작성자 본인, 상점 주인만 삭제 가능
     */
    @DeleteMapping("/{reviewId}")
    public BaseResponse deleteReview(@PathVariable @Positive Long reviewId) {

        log.info("[deleteReview] 리뷰 삭제 요청");
        reviewService.deleteReview(reviewId);

        return new BaseResponse(
                SUCCESS_DELETE_REVIEW
        );
    }
}
