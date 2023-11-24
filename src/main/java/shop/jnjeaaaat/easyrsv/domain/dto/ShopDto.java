package shop.jnjeaaaat.easyrsv.domain.dto;

import lombok.*;
import shop.jnjeaaaat.easyrsv.domain.model.Shop;

import java.time.LocalDateTime;

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
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    /**
     * Shop Entity -> ShopDto
     */
    public static ShopDto from(Shop shop) {
        return ShopDto.builder()
                .id(shop.getId())
                .name(shop.getName())
                .description(shop.getDescription())
                .location(shop.getLocation())
                .createdAt(shop.getCreatedAt())
                .updatedAt(shop.getUpdatedAt())
                .build();
    }
}
