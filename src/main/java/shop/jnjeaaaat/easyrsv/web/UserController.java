package shop.jnjeaaaat.easyrsv.web;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import shop.jnjeaaaat.easyrsv.domain.dto.base.BaseResponse;
import shop.jnjeaaaat.easyrsv.domain.dto.user.AuthChangeRequest;
import shop.jnjeaaaat.easyrsv.domain.dto.user.AuthChangeResponse;
import shop.jnjeaaaat.easyrsv.domain.dto.user.UserDto;
import shop.jnjeaaaat.easyrsv.domain.dto.user.UserModifyRequest;
import shop.jnjeaaaat.easyrsv.service.UserService;

import javax.validation.Valid;
import javax.validation.constraints.Min;

import static shop.jnjeaaaat.easyrsv.domain.dto.base.BaseResponseStatus.*;

@Slf4j
@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/easy-rsv/v1/user")
public class UserController {

    private final UserService userService;

    /*
    email, password 입력해서 PARTNER 권한 추가
    PARTNER 권한 토큰으로 재발행해서 return
     */
    @PutMapping("/partner")
    public BaseResponse<AuthChangeResponse> addPartnerAuthToUser(
            @Valid @RequestBody AuthChangeRequest request) {
        log.info("[addPartnerAuthToUser] 파트너 권한 추가 요청");

        AuthChangeResponse response = userService.addAuthToUser(request);

        return new BaseResponse<>(
                SUCCESS_ADD_PARTNER_AUTH,
                response
        );
    }

    @PutMapping("/{userId}")
    public BaseResponse<UserDto> modifyUserDetails(
            @Min(1) @PathVariable Long userId,
            @RequestBody UserModifyRequest request) {

        log.info("[modifyUserDetails] 유저 정보 수정 요청");

        return new BaseResponse<>(
                SUCCESS_MODIFY_USER,
                userService.modifyUserDetails(userId, request)
        );
    }
}
