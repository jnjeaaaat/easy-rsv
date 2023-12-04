package shop.jnjeaaaat.easyrsv.service;

import shop.jnjeaaaat.easyrsv.domain.dto.review.ReviewInputRequest;
import shop.jnjeaaaat.easyrsv.domain.dto.review.ReviewInputResponse;

public interface ReviewService {

    /**
     * 리뷰 작성
     */
    ReviewInputResponse createReview(ReviewInputRequest request);
}
