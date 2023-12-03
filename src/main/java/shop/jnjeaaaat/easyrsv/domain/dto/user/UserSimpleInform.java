package shop.jnjeaaaat.easyrsv.domain.dto.user;

import lombok.*;

/**
 * 다른 Entity 에서 Shop 에 대한 정보가 필요할 때
 * 사용자가 필요한 내용만 보여줄 수 있는 User 간략정보 Class
 */
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class UserSimpleInform {
    private Long id;
    private String email;
    private String name;
}
