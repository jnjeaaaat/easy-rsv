package shop.jnjeaaaat.easyrsv.domain.dto.user;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class UserRequest {
    private String email;
    private String password;
    private String name;
}
