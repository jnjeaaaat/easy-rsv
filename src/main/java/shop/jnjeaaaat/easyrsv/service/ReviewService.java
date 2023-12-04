package shop.jnjeaaaat.easyrsv.service;

import shop.jnjeaaaat.easyrsv.domain.dto.review.ReviewInputRequest;
import shop.jnjeaaaat.easyrsv.domain.dto.review.ReviewInputResponse;
import shop.jnjeaaaat.easyrsv.domain.dto.review.ReviewModifyRequest;

public interface ReviewService {

    /**
     * 리뷰 작성
     */
    ReviewInputResponse createReview(ReviewInputRequest request);

    /**
     * 리뷰 수정
     */
    ReviewInputResponse modifyReview(Long reviewId, ReviewModifyRequest request);
}
