package shop.jnjeaaaat.easyrsv.domain.dto.shop;

import lombok.*;
import shop.jnjeaaaat.easyrsv.domain.dto.user.OwnerInform;
import shop.jnjeaaaat.easyrsv.domain.model.Shop;

import java.time.LocalDateTime;

/**
 * 사용자에 가깝게 Shop Entity 를 가공한 DTO Class
 */
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ShopDto {

    private Long id;
    private String name;
    private String description;
    private String location;

    private OwnerInform owner;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    /*
     Shop Entity -> ShopDto
     */
    public static ShopDto from(Shop shop) {
        return ShopDto.builder()
                .id(shop.getId())
                .name(shop.getName())
                .description(shop.getDescription())
                .location(shop.getLocation())
                .owner(OwnerInform.builder()
                        .email(shop.getOwner().getEmail())
                        .name(shop.getOwner().getName())
                        .build())
                .createdAt(shop.getCreatedAt())
                .updatedAt(shop.getUpdatedAt())
                .build();
    }
}
