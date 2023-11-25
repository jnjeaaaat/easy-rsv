package shop.jnjeaaaat.easyrsv.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import shop.jnjeaaaat.easyrsv.domain.dto.user.UserRequest;
import shop.jnjeaaaat.easyrsv.domain.model.User;
import shop.jnjeaaaat.easyrsv.domain.repository.UserRepository;
import shop.jnjeaaaat.easyrsv.exception.BaseException;

import static shop.jnjeaaaat.easyrsv.domain.dto.base.BaseResponseStatus.ALREADY_REGISTERED_USER;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    // PasswordEncoder 로 비밀번호 암호화, 복호화
    private final PasswordEncoder passwordEncoder;

    /**
     * 회원가입 Service
     * @param request
     * @return
     */
    public User signUp(UserRequest request) {
        if (isEmailExist(request.getEmail())) {
            throw new BaseException(ALREADY_REGISTERED_USER);
        }

        // user 객체 생성
        User user = User.builder()
                .email(request.getEmail())
                .password(request.getPassword())
                .name(request.getName())
                .build();

        // 비밀번호 암호화 로그
        log.info("비밀번호 암호화 성공 : {}", user.encryptPassword(passwordEncoder));

        // 암호화된 비밀번호로 db 에 저장
        return userRepository.save(
                User.builder()
                        .email(request.getEmail())
                        .password(user.getPassword())
                        .name(request.getName())
                        .build()
        );
    }

    /**
     * 이미 가입된 email 인지 체크
     * @param email
     * @return
     */
    private boolean isEmailExist(String email) {
        return userRepository.findByEmail(email)
                .isPresent();
    }

}
