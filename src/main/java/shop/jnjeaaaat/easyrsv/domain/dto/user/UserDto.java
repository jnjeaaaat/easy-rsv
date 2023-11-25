package shop.jnjeaaaat.easyrsv.domain.dto.user;

import lombok.*;
import shop.jnjeaaaat.easyrsv.domain.model.User;

import java.time.LocalDateTime;

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
    private boolean isPartner;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public static UserDto from(User user) {
        return UserDto.builder()
                .id(user.getId())
                .email(user.getEmail())
                .password(user.getPassword())
                .name(user.getName())
                .isPartner(user.isPartner())
                .build();
    }
}
