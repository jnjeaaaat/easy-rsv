package shop.jnjeaaaat.easyrsv.service;

import shop.jnjeaaaat.easyrsv.domain.dto.sign.SignUpRequest;
import shop.jnjeaaaat.easyrsv.domain.dto.sign.SignInRequest;
import shop.jnjeaaaat.easyrsv.domain.dto.user.UserDto;
import shop.jnjeaaaat.easyrsv.domain.model.User;

public interface SignService {

    UserDto signUp(SignUpRequest request);

    String signInAndCreateToken(SignInRequest request);

}
