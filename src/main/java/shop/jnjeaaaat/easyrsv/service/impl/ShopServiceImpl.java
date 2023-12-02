package shop.jnjeaaaat.easyrsv.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shop.jnjeaaaat.easyrsv.domain.dto.shop.ShopDto;
import shop.jnjeaaaat.easyrsv.domain.dto.shop.ShopInputRequest;
import shop.jnjeaaaat.easyrsv.domain.dto.shop.ShopInputResponse;
import shop.jnjeaaaat.easyrsv.domain.dto.user.OwnerInform;
import shop.jnjeaaaat.easyrsv.domain.model.Shop;
import shop.jnjeaaaat.easyrsv.domain.model.User;
import shop.jnjeaaaat.easyrsv.domain.repository.ShopRepository;
import shop.jnjeaaaat.easyrsv.domain.repository.UserRepository;
import shop.jnjeaaaat.easyrsv.exception.BaseException;
import shop.jnjeaaaat.easyrsv.service.ShopService;
import shop.jnjeaaaat.easyrsv.utils.JwtTokenProvider;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static shop.jnjeaaaat.easyrsv.domain.dto.base.BaseResponseStatus.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class ShopServiceImpl implements ShopService {

    private final JwtTokenProvider jwtTokenProvider;
    private final ShopRepository shopRepository;
    private final UserRepository userRepository;

    /*
     Shop 등록 Service
     */
    @Override
    public ShopInputResponse addShop(ShopInputRequest request) {

        // Header 로부터 token 조회
        Long userId = jwtTokenProvider.getUserIdFromToken();
        log.info("[addShop] 상점 추가 - 요청하는 유저 : {}", userId);

        // 존재하지 않은 유저 validation
        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new BaseException(USER_NOT_FOUND));

        // 등록하려는 유저와, 토큰의 유저가 일치하지 않을 때 validation
        if (!Objects.equals(userId, user.getId())) {
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
    @Override
    public List<ShopDto> getShopByName(String name) {
        log.info("[getShopByName] 상점 이름으로 상점 리스트 조회");

        // 해당 이름의 상점 모두 리턴
        return shopRepository.findAllByName(name)
                .stream()
                .map(ShopDto::from)
                .collect(Collectors.toList());
    }

    /*
     PK로 Shop 하나 조회
     */
    @Override
    public ShopDto getShopById(Long shopId) {
        log.info("[getShopById] 상점 조회 - 상점 id : {}", shopId);

        // 존재하는 상점인지 id 값으로 확인
        return ShopDto.from(shopRepository.findById(shopId)
                .orElseThrow(() -> new BaseException(SHOP_NOT_FOUND))
        );
    }

    /*
    수정할 상점 id,
    상점 이름, 상점 설명, 상점 위치 값을 받아서 수정하는 method

    ownerId 값도 함께 받아서 token 에 저장되어있는 userId 값과 비교
     */
    @Override
    @Transactional
    public ShopDto modifyShopDetails(Long shopId, ShopInputRequest request) {
        log.info("[modifyShopDetails] 상점 정보 수정 - 상점 id : {}", shopId);

        // token 값으로 userId, User 에 등록되어있는지 까지 확인이 됨
        Long userId = jwtTokenProvider.getUserIdFromToken();

        // 상점 유무 확인
        Shop shop = shopRepository.findById(shopId)
                .orElseThrow(() -> new BaseException(SHOP_NOT_FOUND));

        // 토큰 userId 와 상점 등록 유저가 다를 때
        if (!Objects.equals(userId, shop.getOwner().getId())) {
            throw new BaseException(USER_UN_MATCH);
        }
        // 토큰 userId 와 request 의 userId 가 다를 때
        if (!Objects.equals(userId, request.getUserId())) {
            throw new BaseException(USER_UN_MATCH);
        }

        shop.setName(request.getName());
        shop.setDescription(request.getDescription());
        shop.setLocation(request.getLocation());
        shop.setUpdatedAt(LocalDateTime.now());

        return ShopDto.from(shop);

    }

}
