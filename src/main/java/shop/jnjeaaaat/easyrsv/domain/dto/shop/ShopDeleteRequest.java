package shop.jnjeaaaat.easyrsv.domain.dto.shop;

import lombok.*;

import javax.validation.constraints.NotBlank;

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
