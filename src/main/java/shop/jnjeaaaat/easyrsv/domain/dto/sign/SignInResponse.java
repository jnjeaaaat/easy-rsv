package shop.jnjeaaaat.easyrsv.domain.dto.sign;

import lombok.*;

import java.util.List;

/**
 * 로그인 하고 나서
 * email, 권한, jwt Token 을 반환하는
 * Response Class
 */
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SignInResponse {
    private String email;
    private List<String> roles;
    private String jwt;

    public static SignInResponse from(String email, String jwtToken) {
        return SignInResponse.builder()
                .email(email)
                .jwt(jwtToken)
                .build();
    }
}
