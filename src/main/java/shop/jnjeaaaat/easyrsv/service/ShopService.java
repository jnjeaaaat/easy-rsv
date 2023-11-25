package shop.jnjeaaaat.easyrsv.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import shop.jnjeaaaat.easyrsv.domain.dto.shop.ShopRequest;
import shop.jnjeaaaat.easyrsv.domain.model.Shop;
import shop.jnjeaaaat.easyrsv.domain.model.User;
import shop.jnjeaaaat.easyrsv.domain.repository.ShopRepository;
import shop.jnjeaaaat.easyrsv.domain.repository.UserRepository;
import shop.jnjeaaaat.easyrsv.exception.BaseException;

import static shop.jnjeaaaat.easyrsv.domain.dto.base.BaseResponseStatus.*;

@Service
@RequiredArgsConstructor
public class ShopService {

    private final ShopRepository shopRepository;
    private final UserRepository userRepository;

    /**
     * Shop 등록 Service
     */
    public Shop addShop(ShopRequest request) {
        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new BaseException(USER_NOT_FOUND));

        return shopRepository.save(
                Shop.builder()
                        .name(request.getName())
                        .description(request.getDescription())
                        .location(request.getLocation())
                        .owner(user)
                        .build()
        );
    }

}
