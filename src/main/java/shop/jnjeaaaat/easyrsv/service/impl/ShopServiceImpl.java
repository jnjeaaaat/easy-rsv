package shop.jnjeaaaat.easyrsv.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shop.jnjeaaaat.easyrsv.domain.dto.shop.*;
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

/**
 * ShopService interface 구현체 Class
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class ShopServiceImpl implements ShopService {

    private final JwtTokenProvider jwtTokenProvider;
    private final ShopRepository shopRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

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

    /*
    삭제하고자 하는 shopId 값에 해당하는 상점 삭제
    삭제하려면 상점 주인 로그인 할때의 비밀번호와, 삭제 확인 문구가 일치해야함.
     */
    @Override
    @Transactional
    public ShopDeleteResponse deleteShop(Long shopId, ShopDeleteRequest request) {
        log.info("[deleteShop] 상점 삭제 - 상점 id : {}", shopId);

        // 토큰으로부터 받아온 userId
        Long userId = jwtTokenProvider.getUserIdFromToken();

        // shop 존재 유무 확인
        Shop shop = shopRepository.findById(shopId)
                .orElseThrow(() -> new BaseException(SHOP_NOT_FOUND));
        // 비밀번호 비교를 위해 받아온 User 엔티티
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new BaseException(USER_NOT_FOUND));

        // 상점 주인과 접근한 토큰의 user 와 다를 때
        if (!userId.equals(shop.getOwner().getId())) {
            throw new BaseException(USER_UN_MATCH);
        }
        // 토큰의 user 비밀번호와 입력된 비밀번호가 다를 때
        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new BaseException(PASSWORD_UN_MATCH);
        }
        // 삭제 재확인 문구가 다를 때
        if (!request.getCheckAgain().equals("삭제하겠습니다")) {
            throw new BaseException(CHECK_MESSAGE_UN_MATCH);
        }

        // 상점 삭제
        shop.setOwner(null);   // 외래키 제약 조건에 오류가 나지 않게 하기위해
                                // Owner 정보를 먼저 null 로 변경해야한다.
        shopRepository.deleteById(shopId);   // 그리고 나서 delete Shop
        log.info("[deleteShop] 삭제 완료");

        return ShopDeleteResponse.from(
                ShopDto.builder()
                        .id(shopId)
                        .build()
        );
    }

}
