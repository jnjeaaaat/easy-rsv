package shop.jnjeaaaat.easyrsv.domain.dto.review;

import lombok.*;
import shop.jnjeaaaat.easyrsv.domain.dto.user.UserSimpleInform;

/**
 * 리뷰 리스트 조회할때
 * 사용자에게 필요한 값 Response Class
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReviewShopResponse {

    private Long id;   // 리뷰 id
    private UserSimpleInform user;  // 리뷰 작성 유저
    private Long shopId;   // 리뷰 작성된 상점 id
    private String description;   // 리뷰 내용

    public static ReviewShopResponse from(ReviewDto reviewDto) {

        return ReviewShopResponse.builder()
                .id(reviewDto.getId())          // 리뷰 id
                .user(reviewDto.getUser())      // 리뷰 작성 유저
                .shopId(reviewDto.getShop().getId())    // 리뷰 작성된 상점 id
                .description(reviewDto.getDescription()) // 리뷰 내용
                .build();
    }
}
