package shop.jnjeaaaat.easyrsv.domain.dto.shop;

import lombok.*;

import javax.validation.constraints.NotBlank;

/**
 * 상점을 삭제할 때 입력해야 하는 Request Class
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ShopDeleteRequest {
    @NotBlank
    private String password;
    @NotBlank
    private String checkAgain;
}
