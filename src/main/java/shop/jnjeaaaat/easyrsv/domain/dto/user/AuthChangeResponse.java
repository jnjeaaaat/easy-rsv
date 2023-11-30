package shop.jnjeaaaat.easyrsv.domain.dto.user;

import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class AuthChangeResponse {
    private String email;
    private String name;
    private List<String> roles;
    private String newToken;

    public static AuthChangeResponse from(UserDto userDto, String newToken) {
        return AuthChangeResponse.builder()
                .email(userDto.getEmail())
                .name(userDto.getName())
                .roles(userDto.getRoles())
                .newToken(newToken)
                .build();
    }
}
