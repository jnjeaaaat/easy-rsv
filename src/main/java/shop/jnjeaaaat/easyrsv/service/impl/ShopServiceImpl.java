package shop.jnjeaaaat.easyrsv.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import shop.jnjeaaaat.easyrsv.domain.dto.shop.ShopDto;
import shop.jnjeaaaat.easyrsv.domain.dto.shop.ShopInputRequest;
import shop.jnjeaaaat.easyrsv.domain.dto.shop.ShopInputResponse;
import shop.jnjeaaaat.easyrsv.domain.model.Shop;
import shop.jnjeaaaat.easyrsv.domain.model.User;
import shop.jnjeaaaat.easyrsv.domain.repository.ShopRepository;
import shop.jnjeaaaat.easyrsv.domain.repository.UserRepository;
import shop.jnjeaaaat.easyrsv.exception.BaseException;
import shop.jnjeaaaat.easyrsv.service.ShopService;

import java.util.List;
import java.util.Objects;
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
    public ShopInputResponse addShop(Long userId, ShopInputRequest request) {

        // 존재하지 않은 유저 validation
        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new BaseException(USER_NOT_FOUND));

        // 등록하려는 유저와, 토큰의 유저가 일치하지 않을 때 validation
        if (!Objects.equals(userId, request.getUserId())) {
            throw new BaseException(USER_UN_MATCH);
        }

        return ShopInputResponse.from(
                ShopDto.from(
                        shopRepository.save(
                                Shop.builder()
                                        .name(request.getName())
                                        .description(request.getDescription())
                                        .location(request.getLocation())
                                        .owner(user)
                                        .build()
                        )
                )
        );
    }

    /*
     이름(name)으로 Shop list 조회
     */
    public List<ShopDto> getShopByName(String name) {

        // 해당 이름의 상점 모두 리턴
        return shopRepository.findAllByName(name)
                .stream()
                .map(ShopDto::from)
                .collect(Collectors.toList());
    }

    /*
     PK로 Shop 하나 조회
     */
    public ShopDto getShopById(Long id) {

        // 존재하는 상점인지 id 값으로 확인
        return ShopDto.from(shopRepository.findById(id)
                .orElseThrow(() -> new BaseException(SHOP_NOT_FOUND))
        );
    }

}
