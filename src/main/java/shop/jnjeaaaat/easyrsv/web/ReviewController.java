package shop.jnjeaaaat.easyrsv.web;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import shop.jnjeaaaat.easyrsv.domain.dto.base.BaseResponse;
import shop.jnjeaaaat.easyrsv.domain.dto.review.ReviewInputRequest;
import shop.jnjeaaaat.easyrsv.domain.dto.review.ReviewInputResponse;
import shop.jnjeaaaat.easyrsv.service.ReviewService;

import javax.validation.Valid;

import static shop.jnjeaaaat.easyrsv.domain.dto.base.BaseResponseStatus.SUCCESS_WRITE_REVIEW;

@Slf4j
@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/easy-rsv/v1/review")
public class ReviewController {

    private final ReviewService reviewService;

    /*
    예약 id, 리뷰 내용 Request
    유저 email, 상점 정보, 리뷰 작성 날짜 return
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
}
