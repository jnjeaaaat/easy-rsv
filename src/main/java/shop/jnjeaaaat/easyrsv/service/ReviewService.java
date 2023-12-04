package shop.jnjeaaaat.easyrsv.service;

import shop.jnjeaaaat.easyrsv.domain.dto.review.*;

import java.util.List;

/**
 * ReviewService 인터페이스
 */
public interface ReviewService {

    /**
     * 리뷰 작성
     */
    ReviewInputResponse createReview(ReviewInputRequest request);

    /**
     * 리뷰 수정
     */
    ReviewInputResponse modifyReview(Long reviewId, ReviewModifyRequest request);

    /**
     * 해당 매장의 리뷰 리스트 조회
     */
    List<ReviewShopResponse> getShopReviews(Long shopId);

    /**
     * 해당 리뷰 하나 조회
     */
    ReviewDto getReview(Long reviewId);

    /**
     * 해당 리뷰 삭제
     */
    void deleteReview(Long reviewId);
}
