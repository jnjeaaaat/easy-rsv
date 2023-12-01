package shop.jnjeaaaat.easyrsv.domain.dto.sign;

import lombok.*;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

/**
 * 로그인 입력값을 위한 Request Class
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class SignInRequest {

    @Email
    private String email;

    @NotBlank(message = "비밀번호를 입력해주세요.")
    private String password;
}
