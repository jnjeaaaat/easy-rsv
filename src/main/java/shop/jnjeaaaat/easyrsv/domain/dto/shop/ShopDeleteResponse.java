package shop.jnjeaaaat.easyrsv.domain.dto.shop;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ShopDeleteResponse {
    private Long shopId;

    public static ShopDeleteResponse from(ShopDto shopDto) {
        return ShopDeleteResponse.builder()
                .shopId(shopDto.getId())
                .build();
    }
}
