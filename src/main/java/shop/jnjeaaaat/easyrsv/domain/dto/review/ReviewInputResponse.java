package shop.jnjeaaaat.easyrsv.domain.dto.review;

import lombok.*;
import shop.jnjeaaaat.easyrsv.domain.dto.shop.ShopSimpleInform;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReviewInputResponse {

    private String email;
    private ShopSimpleInform shop;
    private LocalDateTime createdAt;

    public static ReviewInputResponse from(ReviewDto reviewDto) {
        return ReviewInputResponse.builder()
                .email(reviewDto.getUser().getEmail())   // 리뷰 작성 유저
                .shop(reviewDto.getShop())          // 리뷰 작성 상점
                .createdAt(reviewDto.getCreatedAt())   // 리뷰 작성 날짜
                .build();
    }
}
