package shop.jnjeaaaat.easyrsv.domain.dto.sign;

import lombok.*;
import shop.jnjeaaaat.easyrsv.domain.dto.user.UserDto;

import java.util.List;

/**
 * 회원가입 하고 나서
 * email, name, roles 이 정상적으로 등록되었는지
 * 확인할 수 있는 Class
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SignUpResponse {

    private String email;
    private String name;
    private List<String> roles;

    public static SignUpResponse from(UserDto userDto) {
        return SignUpResponse.builder()
                .email(userDto.getEmail())
                .name(userDto.getName())
                .roles(userDto.getRoles())
                .build();
    }
}
