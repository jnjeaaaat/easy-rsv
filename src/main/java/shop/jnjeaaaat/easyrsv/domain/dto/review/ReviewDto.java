package shop.jnjeaaaat.easyrsv.domain.dto.review;

import lombok.*;
import shop.jnjeaaaat.easyrsv.domain.dto.shop.ShopSimpleInform;
import shop.jnjeaaaat.easyrsv.domain.dto.user.OwnerInform;
import shop.jnjeaaaat.easyrsv.domain.dto.user.UserSimpleInform;
import shop.jnjeaaaat.easyrsv.domain.model.Review;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReviewDto {

    private Long id;
    private String description;
    private UserSimpleInform user;
    private ShopSimpleInform shop;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public static ReviewDto from(Review review) {
        return ReviewDto.builder()
                .id(review.getId())  // 리뷰 id
                .description(review.getDescription())   // 리뷰 내용

                .user(UserSimpleInform.builder()   // 리뷰 쓴 유저 정보
                        .id(review.getUser().getId())
                        .email(review.getUser().getEmail())
                        .name(review.getUser().getName())
                        .build())

                .shop(ShopSimpleInform.builder()  // 리뷰 쓰여진 상점
                        .id(review.getShop().getId())
                        .name(review.getShop().getName())
                        .description(review.getShop().getDescription())
                        .location(review.getShop().getLocation())
                        .owner(OwnerInform.builder()
                                .id(review.getShop().getOwner().getId())
                                .email(review.getShop().getOwner().getEmail())
                                .name(review.getShop().getOwner().getName())
                                .build())
                        .createdAt(review.getShop().getCreatedAt())
                        .build())

                .createdAt(review.getCreatedAt())  // 리뷰 새로 쓴 날짜
                .updatedAt(review.getUpdatedAt())  // 리뷰 수정 날짜
                .build();
    }
}
