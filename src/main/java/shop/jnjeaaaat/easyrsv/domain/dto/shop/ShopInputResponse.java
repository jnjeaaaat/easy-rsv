package shop.jnjeaaaat.easyrsv.domain.dto.shop;

import lombok.*;

/**
 * 상점 등록하고 나서 정상적으로 등록되었는지
 * 사용자가 확인할 수 있는 정보 Class
 */
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
