package shop.jnjeaaaat.easyrsv.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import shop.jnjeaaaat.easyrsv.domain.dto.sign.SignInRequest;
import shop.jnjeaaaat.easyrsv.domain.dto.sign.SignInResponse;
import shop.jnjeaaaat.easyrsv.domain.dto.sign.SignUpRequest;
import shop.jnjeaaaat.easyrsv.domain.dto.user.UserDto;
import shop.jnjeaaaat.easyrsv.domain.model.User;
import shop.jnjeaaaat.easyrsv.domain.repository.UserRepository;
import shop.jnjeaaaat.easyrsv.exception.BaseException;
import shop.jnjeaaaat.easyrsv.service.SignService;
import shop.jnjeaaaat.easyrsv.utils.JwtTokenProvider;

import java.util.Collections;

import static shop.jnjeaaaat.easyrsv.domain.dto.base.BaseResponseStatus.*;

/**
 * SignService interface 구현체 Class
 * SignUp, SignIn 기능만 담당
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class SignServiceImpl implements SignService {

    private final UserRepository userRepository;
    private final JwtTokenProvider jwtTokenProvider;
    private final PasswordEncoder passwordEncoder;

    /*
    email, password, name 받아서 회원가입
    roles은 기본적으로 ROLE_USER로 등록
     */
    @Override
    public UserDto signUp(SignUpRequest request) {
        log.info("[getSignUpResult] 회원 가입 정보 전달");

        // 이미 가입한 email validation
        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new BaseException(ALREADY_REGISTERED_USER);
        }

        return UserDto.from(userRepository.save(User.builder()
                        .email(request.getEmail())
                        .password(passwordEncoder.encode(request.getPassword()))
                        .name(request.getName())
                        .roles(Collections.singletonList("ROLE_USER"))
                        .build()
                )
        );
    }

    /*
    email, password 값 받아서 user 정보 확인,
    email, roles 값으로 토큰 발행,
    SignInResponse form 에 맞춰서 리턴
    */
    @Override
    public SignInResponse signInAndCreateToken(SignInRequest request) {
        log.info("[getSignInResult] 로그인 정보 전달");
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new BaseException(USER_NOT_FOUND));
        log.info("[getSignInResult] Email : {}", request.getEmail());

        log.info("[getSignInResult] 패스워드 비교");
        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new BaseException(PASSWORD_UN_MATCH);
        }
        log.info("[getSignInResult] 패스워드 일치");

        // 토큰 발행
        String token =
                jwtTokenProvider
                        .createToken(user.getEmail(), user.getId(), user.getRoles());

        // return SignInResponse Builder
        return SignInResponse.builder()
                .email(user.getEmail())
                .roles(user.getRoles())
                .jwt(token)
                .build();
    }
}
