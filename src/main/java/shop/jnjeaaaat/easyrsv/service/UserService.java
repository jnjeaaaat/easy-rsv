package shop.jnjeaaaat.easyrsv.service;

import shop.jnjeaaaat.easyrsv.domain.dto.user.AuthChangeRequest;
import shop.jnjeaaaat.easyrsv.domain.dto.user.AuthChangeResponse;
import shop.jnjeaaaat.easyrsv.domain.dto.user.UserDto;
import shop.jnjeaaaat.easyrsv.domain.dto.user.UserModifyRequest;

/**
 * UserService interface
 */
public interface UserService {

    /**
     * 유저에 권한 추가
     */
    AuthChangeResponse addAuthToUser(AuthChangeRequest request);

    /**
     * 유저 정보 수정
     */
    UserDto modifyUserDetails(Long userId, UserModifyRequest request);


}
