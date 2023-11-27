package shop.jnjeaaaat.easyrsv.domain.dto.sign;

import lombok.*;
import shop.jnjeaaaat.easyrsv.domain.dto.user.UserDto;

import java.util.List;

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
