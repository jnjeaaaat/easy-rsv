package shop.jnjeaaaat.easyrsv.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import shop.jnjeaaaat.easyrsv.domain.dto.shop.ShopDto;
import shop.jnjeaaaat.easyrsv.domain.dto.shop.ShopRequest;
import shop.jnjeaaaat.easyrsv.domain.model.Shop;
import shop.jnjeaaaat.easyrsv.domain.model.User;
import shop.jnjeaaaat.easyrsv.domain.repository.ShopRepository;
import shop.jnjeaaaat.easyrsv.domain.repository.UserRepository;
import shop.jnjeaaaat.easyrsv.exception.BaseException;
import shop.jnjeaaaat.easyrsv.service.ShopService;

import java.util.List;
import java.util.stream.Collectors;

import static shop.jnjeaaaat.easyrsv.domain.dto.base.BaseResponseStatus.*;

@Service
@RequiredArgsConstructor
public class ShopServiceImpl implements ShopService {

    private final ShopRepository shopRepository;
    private final UserRepository userRepository;

    /*
     Shop 등록 Service
     */
    public ShopDto addShop(ShopRequest request) {
        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new BaseException(USER_NOT_FOUND));

        return ShopDto.from(shopRepository.save(
                Shop.builder()
                        .name(request.getName())
                        .description(request.getDescription())
                        .location(request.getLocation())
                        .owner(user)
                        .build()
                )
        );
    }

    /*
     이름(name)으로 Shop list 조회
     */
    public List<ShopDto> getShopByName(String name) {

        return shopRepository.findAllByName(name)
                .stream()
                .map(ShopDto::from)
                .collect(Collectors.toList());
    }

    /*
     PK로 Shop 하나 조회
     */
    public ShopDto getShopById(Long id) {
        return ShopDto.from(shopRepository.findById(id)
                .orElseThrow(() -> new BaseException(SHOP_NOT_FOUND))
        );
    }

}
