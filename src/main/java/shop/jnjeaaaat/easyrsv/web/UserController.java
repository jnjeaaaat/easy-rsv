package shop.jnjeaaaat.easyrsv.web;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import shop.jnjeaaaat.easyrsv.domain.dto.base.BaseResponse;
import shop.jnjeaaaat.easyrsv.domain.dto.user.UserRequest;
import shop.jnjeaaaat.easyrsv.domain.dto.user.UserResponse;
import shop.jnjeaaaat.easyrsv.service.UserService;

import static shop.jnjeaaaat.easyrsv.domain.dto.base.BaseResponseStatus.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/easy-rsv/v1/user")
public class UserController {

    private final UserService userService;

    @PostMapping("")
    public BaseResponse<UserResponse> signUp(@RequestBody UserRequest request) {
        log.info("user try to Sign Up");

        return new BaseResponse<>(
                SUCCESS_SIGN_UP,
                UserResponse.from(userService.signUp(request))
        );
    }
}
