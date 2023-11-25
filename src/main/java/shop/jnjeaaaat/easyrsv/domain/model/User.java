package shop.jnjeaaaat.easyrsv.domain.model;

import lombok.*;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class User extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 이메일
    private String email;
    // 비밀번호
    private String password;
    // 사용자 이름
    private String name;
    // partner 인지 확인
    private boolean isPartner;

    // 비밀번호 암호화
    public String encryptPassword(PasswordEncoder encoder) {
        this.password = encoder.encode(this.password);
        return this.password;
    }

}
