package shop.jnjeaaaat.easyrsv.service;

import shop.jnjeaaaat.easyrsv.domain.dto.user.AuthChangeRequest;
import shop.jnjeaaaat.easyrsv.domain.dto.user.AuthChangeResponse;

public interface UserService {

    AuthChangeResponse addAuthToUser(AuthChangeRequest request);


}
