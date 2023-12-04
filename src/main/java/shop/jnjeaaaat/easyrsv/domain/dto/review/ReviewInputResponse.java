package shop.jnjeaaaat.easyrsv.domain.dto.review;

import lombok.*;
import shop.jnjeaaaat.easyrsv.domain.dto.shop.ShopSimpleInform;

import java.time.LocalDateTime;

/**
 * 리뷰 작성 후
 * 사용자가 받을 Response Class
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReviewInputResponse {

    private String email;
    private ShopSimpleInform shop;
    private String description;
    private LocalDateTime updatedAt;

    public static ReviewInputResponse from(ReviewDto reviewDto) {
        return ReviewInputResponse.builder()
                .email(reviewDto.getUser().getEmail())   // 리뷰 작성 유저
                .shop(reviewDto.getShop())          // 리뷰 작성 상점
                .description(reviewDto.getDescription()) // 리뷰 내용
                .updatedAt(reviewDto.getUpdatedAt())   // 리뷰 작성 날짜
                .build();
    }
}
