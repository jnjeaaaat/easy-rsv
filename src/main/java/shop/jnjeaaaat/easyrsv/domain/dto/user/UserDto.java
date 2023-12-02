package shop.jnjeaaaat.easyrsv.domain.dto.user;

import lombok.*;
import shop.jnjeaaaat.easyrsv.domain.model.User;

import java.time.LocalDateTime;
import java.util.List;

/**
 * User Entity 를
 * Client 에 가깝게 가공한 UserDto
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserDto {
    private Long id;
    private String email;
    private String password;
    private String name;
    private List<String> roles;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public static UserDto from(User user) {
        return UserDto.builder()
                .id(user.getId())
                .email(user.getEmail())
                .password(user.getPassword())
                .name(user.getName())
                .roles(user.getRoles())
                .createdAt(user.getCreatedAt())
                .updatedAt(user.getUpdatedAt())
                .build();
    }
}
