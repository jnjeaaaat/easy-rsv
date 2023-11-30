package shop.jnjeaaaat.easyrsv.service;

import shop.jnjeaaaat.easyrsv.domain.dto.sign.SignInRequest;
import shop.jnjeaaaat.easyrsv.domain.dto.sign.SignInResponse;
import shop.jnjeaaaat.easyrsv.domain.dto.sign.SignUpRequest;
import shop.jnjeaaaat.easyrsv.domain.dto.user.UserDto;

public interface SignService {

    UserDto signUp(SignUpRequest request);

    SignInResponse signInAndCreateToken(SignInRequest request);

}
