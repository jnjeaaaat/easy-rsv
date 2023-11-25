package shop.jnjeaaaat.easyrsv.domain.dto.user;

import lombok.*;
import shop.jnjeaaaat.easyrsv.domain.model.User;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserResponse {

    private String email;
    private String name;
    private boolean isPartner;

    public static UserResponse from(User user) {
        return UserResponse.builder()
                .email(user.getEmail())
                .name(user.getName())
                .isPartner(user.isPartner())
                .build();
    }
}
