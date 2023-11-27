package shop.jnjeaaaat.easyrsv.domain.dto.sign;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SignInResponse {
    private String email;
    private String jwt;

    public static SignInResponse from(String email, String jwtToken) {
        return SignInResponse.builder()
                .email(email)
                .jwt(jwtToken)
                .build();
    }
}
