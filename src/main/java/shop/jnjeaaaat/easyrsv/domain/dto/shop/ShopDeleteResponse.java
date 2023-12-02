package shop.jnjeaaaat.easyrsv.domain.dto.shop;

import lombok.*;

/**
 * 상점을 삭제하고 나서 정상적으로 삭제가 되었는지
 * 사용자가 확인할 수 있는 Class
 */
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
