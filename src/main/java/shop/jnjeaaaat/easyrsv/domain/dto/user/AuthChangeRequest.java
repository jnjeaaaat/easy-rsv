package shop.jnjeaaaat.easyrsv.domain.dto.user;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class AuthChangeRequest {
    private String email;
    private String password;
    private String role;
}
