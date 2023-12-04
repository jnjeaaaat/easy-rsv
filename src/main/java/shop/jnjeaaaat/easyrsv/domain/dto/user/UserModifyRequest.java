package shop.jnjeaaaat.easyrsv.domain.dto.user;

import lombok.*;

import javax.validation.constraints.NotBlank;

/**
 * User 정보 수정 Request Class
 */
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
