package shop.jnjeaaaat.easyrsv.web;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import shop.jnjeaaaat.easyrsv.domain.dto.base.BaseResponse;
import shop.jnjeaaaat.easyrsv.domain.dto.user.AuthChangeRequest;
import shop.jnjeaaaat.easyrsv.domain.dto.user.AuthChangeResponse;
import shop.jnjeaaaat.easyrsv.service.UserService;

import javax.validation.Valid;

import static shop.jnjeaaaat.easyrsv.domain.dto.base.BaseResponseStatus.SUCCESS_ADD_ADMIN_AUTH;

/**
 * 관리자 (Admin) 만 사용할 수 있는 api 를 구현하는 Controller
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/easy-rsv/admin")
public class AdminController {

    private final UserService userService;

    /*
    email, password 입력해서 ADMIN 권한 추가
    ADMIN 권한 토큰으로 재발행해서 return
     */
    @PutMapping("/auth")
    public BaseResponse<AuthChangeResponse> addAdminAuthToUser(
            @Valid @RequestBody AuthChangeRequest request) {
        log.info("[addAdminAuthToUser] 새로운 권한 추가 요청");

        AuthChangeResponse response = userService.addAuthToUser(request);

        return new BaseResponse<>(
                SUCCESS_ADD_ADMIN_AUTH,
                response
        );

    }
}
