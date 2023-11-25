package shop.jnjeaaaat.easyrsv.domain.dto.shop;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ShopRequest {
    String name;
    String description;
    String location;
    Long userId;
}
