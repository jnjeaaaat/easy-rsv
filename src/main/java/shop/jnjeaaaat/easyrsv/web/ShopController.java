package shop.jnjeaaaat.easyrsv.web;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import shop.jnjeaaaat.easyrsv.domain.dto.base.BaseResponse;
import shop.jnjeaaaat.easyrsv.domain.dto.shop.ShopDto;
import shop.jnjeaaaat.easyrsv.domain.dto.shop.ShopInputRequest;
import shop.jnjeaaaat.easyrsv.domain.dto.shop.ShopInputResponse;
import shop.jnjeaaaat.easyrsv.service.ShopService;
import shop.jnjeaaaat.easyrsv.utils.JwtTokenProvider;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import java.util.List;

import static shop.jnjeaaaat.easyrsv.domain.dto.base.BaseResponseStatus.*;

@Slf4j
@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/easy-rsv/v1/shop")
public class ShopController {

    private final JwtTokenProvider jwtTokenProvider;
    private final ShopService shopService;

    /*
     Shop 등록 Controller
     */
    @PostMapping("")
    public BaseResponse<ShopInputResponse> addShop(
            @Valid @RequestBody ShopInputRequest request) {

        log.info("[addShop] 상점 추가 시도");
        Long userId = jwtTokenProvider.getUserIdFromToken();
        log.info("등록하는 유저 id : {}", userId);

        return new BaseResponse<>(
                SUCCESS_ADD_SHOP,
                shopService.addShop(userId, request)
        );
    }

    /*
     해당 이름 (name) 의 Shop List 조회
     */
    @GetMapping("")
    public BaseResponse<List<ShopDto>> getShopListByName(@RequestParam String name) {

        return new BaseResponse<>(
                GET_SHOP_LIST_BY_NAME,
                shopService.getShopByName(name)
        );
    }

    /*
     해당 id 인 Shop 조회
     */
    @GetMapping("/{shopId}")
    public BaseResponse<ShopDto> getShopById(
            @PathVariable("shopId") @Min(1) Long shopId) {

        return new BaseResponse<>(
                GET_SHOP_BY_ID,
                shopService.getShopById(shopId)
        );
    }
}
