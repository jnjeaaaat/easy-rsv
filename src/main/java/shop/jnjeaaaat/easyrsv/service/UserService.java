package shop.jnjeaaaat.easyrsv.service;

import shop.jnjeaaaat.easyrsv.domain.dto.user.AuthChangeRequest;
import shop.jnjeaaaat.easyrsv.domain.dto.user.AuthChangeResponse;
import shop.jnjeaaaat.easyrsv.domain.dto.user.UserDto;
import shop.jnjeaaaat.easyrsv.domain.dto.user.UserModifyRequest;

public interface UserService {

    AuthChangeResponse addAuthToUser(AuthChangeRequest request);

    UserDto modifyUserDetails(Long userId, UserModifyRequest request);


}
