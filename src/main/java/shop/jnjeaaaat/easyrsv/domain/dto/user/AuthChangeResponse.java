package shop.jnjeaaaat.easyrsv.domain.dto.user;

import lombok.*;

import java.util.List;

/**
 * 유저 권한이 변경되고 나서 잘 변경되었는지 확인할 수 있는 Response class
 */
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
