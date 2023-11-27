package shop.jnjeaaaat.easyrsv.domain.dto.sign;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class SignUpRequest {
    private String email;
    private String password;
    private String name;
}
