package shop.jnjeaaaat.easyrsv.domain.dto.shop;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ShopInputResponse {
    private String name;
    private String description;
    private String location;

    public static ShopInputResponse from(ShopDto shopDto) {
        return ShopInputResponse.builder()
                .name(shopDto.getName())
                .description(shopDto.getDescription())
                .location(shopDto.getLocation())
                .build();
    }
}
