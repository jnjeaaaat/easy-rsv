package shop.jnjeaaaat.easyrsv.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shop.jnjeaaaat.easyrsv.domain.dto.user.AuthChangeRequest;
import shop.jnjeaaaat.easyrsv.domain.dto.user.AuthChangeResponse;
import shop.jnjeaaaat.easyrsv.domain.dto.user.UserDto;
import shop.jnjeaaaat.easyrsv.domain.dto.user.UserModifyRequest;
import shop.jnjeaaaat.easyrsv.domain.model.User;
import shop.jnjeaaaat.easyrsv.domain.repository.UserRepository;
import shop.jnjeaaaat.easyrsv.exception.BaseException;
import shop.jnjeaaaat.easyrsv.service.UserService;
import shop.jnjeaaaat.easyrsv.utils.JwtTokenProvider;

import java.time.LocalDateTime;
import java.util.Objects;

import static shop.jnjeaaaat.easyrsv.domain.dto.base.BaseResponseStatus.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final JwtTokenProvider jwtTokenProvider;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    /*
    이메일, 패스워드를 param 으로 받아서
    roles list 에 ADMIN 권한 추가
    새로운 token 발행 후 return
     */
    @Override
    @Transactional
    public AuthChangeResponse addAuthToUser(AuthChangeRequest request) {
        log.info("[addAuthToUser] 새로운 권한 추가 시작");

        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new BaseException(USER_NOT_FOUND));

        // 이메일, 비밀번호 확인
        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new BaseException(PASSWORD_UN_MATCH);
        }
        // 이미 관리자 계정이라면 더이상 추가 x
        if (user.getRoles().stream().anyMatch(str -> str.equals("ROLE_ADMIN"))) {
            throw new BaseException(ALREADY_ADMIN_ACCOUNT);
        }
        // 이미 파트너 계정이라면 더이상 추가 x
        if (user.getRoles().stream().anyMatch(str -> str.equals("ROLE_PARTNER"))) {
            throw new BaseException(ALREADY_PARTNER_ACCOUNT);
        }

        // 요청 권한이 ADMIN 일 때 ADMIN 권한 추가
        if (request.getRole().equals("ADMIN")) {
            log.info("[addAdminAuthToUser] 관리자 권한 추가 시도");
            user.getRoles().add("ROLE_ADMIN");
            log.info("[addAdminAuthToUser] 관리자 권한 추가 완료");
        }

        // 요청 권한이 PARTNER 일 때 PARTNER 권한 추가
        if (request.getRole().equals("PARTNER")) {
            log.info("[addPartnerAuthToUser] 파트너 권한 추가 시도");
            user.getRoles().add("ROLE_PARTNER");
            log.info("[addPartnerAuthToUser] 파트너 권한 추가 완료");
        }

        user.setUpdatedAt(LocalDateTime.now());

        // 새로운 권한을 추가한 새로운 토큰 발행
        String newToken =
                jwtTokenProvider
                        .createToken(user.getEmail(), user.getId(), user.getRoles());
        log.info("[addAuthToUser] 새로운 권한 추가 끝");

        // AuthChangeResponse Builder
        return AuthChangeResponse.builder()
                .email(user.getEmail())
                .name(user.getName())
                .roles(user.getRoles())
                .newToken(newToken)
                .build();
    }

    /*
    PathVariable 로 userId, Request Dto 에는 비밀번호, 이름이 있다.
    User 를 조회하고 UserDto 로 가공해서 리턴
     */
    @Override
    @Transactional
    public UserDto modifyUserDetails(Long userId, UserModifyRequest request) {
        log.info("[modifyUserDetails] 유저 정보 수정 - 요청한 유저 id : {}", jwtTokenProvider.getUserIdFromToken());

        // 토큰의 userId 와 입력받은 userId 비교 -> 본인만 정보를 수정할 수 있음.
        if (!Objects.equals(userId, jwtTokenProvider.getUserIdFromToken())) {
            throw new BaseException(USER_UN_MATCH);
        }

        // 존재하는 유저인지 확인
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new BaseException(USER_NOT_FOUND));

        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setName(request.getName());
        user.setUpdatedAt(LocalDateTime.now());

        return UserDto.builder()
                .id(user.getId())
                .email(user.getEmail())
                .password(request.getPassword())
                .name(user.getName())
                .roles(user.getRoles())
                .createdAt(user.getCreatedAt())
                .updatedAt(user.getUpdatedAt())
                .build();
    }
}