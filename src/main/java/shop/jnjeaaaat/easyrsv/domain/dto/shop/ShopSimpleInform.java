package shop.jnjeaaaat.easyrsv.domain.dto.shop;

import lombok.*;
import shop.jnjeaaaat.easyrsv.domain.dto.user.OwnerInform;

import java.time.LocalDateTime;

/**
 * 다른 Entity 에서 Shop 에 대한 정보가 필요할 때
 * 사용자가 필요한 내용만 보여줄 수 있는 Shop 간략정보 Class
 */
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class ShopSimpleInform {
    private Long id;
    private String name;
    private String description;
    private String location;
    private OwnerInform owner;
    private LocalDateTime createdAt;
}
