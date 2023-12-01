package shop.jnjeaaaat.easyrsv.domain.dto.user;

import lombok.*;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserModifyRequest {

    @NotBlank
    private String password;

    @NotBlank
    private String name;

}
