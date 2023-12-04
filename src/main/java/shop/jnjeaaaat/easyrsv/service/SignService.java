package shop.jnjeaaaat.easyrsv.service;

import shop.jnjeaaaat.easyrsv.domain.dto.sign.SignInRequest;
import shop.jnjeaaaat.easyrsv.domain.dto.sign.SignInResponse;
import shop.jnjeaaaat.easyrsv.domain.dto.sign.SignUpRequest;
import shop.jnjeaaaat.easyrsv.domain.dto.user.UserDto;

/**
 * SignService interface
 */
public interface SignService {

    /**
     * 회원가입
     */
    UserDto signUp(SignUpRequest request);

    /**
     * 로그인
     */
    SignInResponse signInAndCreateToken(SignInRequest request);

}
