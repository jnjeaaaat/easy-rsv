package shop.jnjeaaaat.easyrsv.web;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import shop.jnjeaaaat.easyrsv.domain.dto.base.BaseResponse;
import shop.jnjeaaaat.easyrsv.domain.dto.sign.SignInRequest;
import shop.jnjeaaaat.easyrsv.domain.dto.sign.SignInResponse;
import shop.jnjeaaaat.easyrsv.domain.dto.sign.SignUpRequest;
import shop.jnjeaaaat.easyrsv.domain.dto.sign.SignUpResponse;
import shop.jnjeaaaat.easyrsv.exception.BaseException;
import shop.jnjeaaaat.easyrsv.service.SignService;

import static shop.jnjeaaaat.easyrsv.domain.dto.base.BaseResponseStatus.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/easy-rsv/v1")
public class SignController {

    private final SignService signService;

    /*
    회원가입 API
    Request로 email, password, name 받아서
    성공 메세지, email, name, isPartner 반환
     */
    @PostMapping("/sign-up")
    public BaseResponse<SignUpResponse> signUp(@RequestBody SignUpRequest request) {
        log.info("user try to Sign Up");

        return new BaseResponse<>(
                SUCCESS_SIGN_UP,
                SignUpResponse.from(signService.signUp(request)) // User를 UserReponse으로 변환해서 반환
        );
    }

    /*
    request 값으로 email, password 받아서 로그인
    토큰 발행
     */
    @PostMapping("/sign-in")
    public BaseResponse<SignInResponse> signIn(@RequestBody SignInRequest request) {
        String token = signService.signInAndCreateToken(request);

        return new BaseResponse<>(
                SUCCESS_SIGN_UP,
                SignInResponse.from(
                        request.getEmail(),
                        token
                )
        );
    }

    @GetMapping("/exception")
    public void exceptionTest() throws BaseException {
        throw new BaseException(ACCESS_DENIED);
    }

}
