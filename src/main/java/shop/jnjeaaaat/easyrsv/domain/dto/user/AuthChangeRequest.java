package shop.jnjeaaaat.easyrsv.domain.dto.user;

import lombok.*;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

/**
 * 유저 권한을 변경하고 싶을 때 쓰이는 Request class
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class AuthChangeRequest {

    @Email
    private String email;

    @NotBlank(message = "비밀번호를 입력해주세요.")
    private String password;

    @NotBlank(message = "추가하고자 하는 권한을 입력해주세요.")
    private String role;
}
