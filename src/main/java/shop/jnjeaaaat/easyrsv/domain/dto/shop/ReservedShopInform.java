package shop.jnjeaaaat.easyrsv.domain.dto.shop;

import lombok.*;

/**
 * 사용자가 상점을 예약하고 정상적으로 예약이 되었다면
 * 받아서 확인할 수 있는 정보 Class
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReservedShopInform {
    private String name;
    private String location;
}
