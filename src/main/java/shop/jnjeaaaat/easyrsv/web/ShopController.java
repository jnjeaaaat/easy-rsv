package shop.jnjeaaaat.easyrsv.web;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import shop.jnjeaaaat.easyrsv.domain.dto.shop.ShopDto;
import shop.jnjeaaaat.easyrsv.domain.dto.shop.ShopRequest;
import shop.jnjeaaaat.easyrsv.domain.dto.base.BaseResponse;
import shop.jnjeaaaat.easyrsv.service.ShopProvider;
import shop.jnjeaaaat.easyrsv.service.ShopService;

import java.util.List;
import java.util.stream.Collectors;

import static shop.jnjeaaaat.easyrsv.domain.dto.base.BaseResponseStatus.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/easy-rsv/v1/shop")
public class ShopController {

    private final ShopService shopService;
    private final ShopProvider shopProvider;

    /**
     * Shop 등록 Controller
     */
    @PostMapping("")
    public BaseResponse<ShopDto> addShop(@RequestBody ShopRequest request) {
        return new BaseResponse<>(
                SUCCESS_ADD_SHOP,
                ShopDto.from(shopService.addShop(request))
        );
    }

    /**
     * 해당 이름 (name) 의 Shop List 조회
     */
    @GetMapping("")
    public BaseResponse<List<ShopDto>> getShopListByName(@RequestParam String name) {

        List<ShopDto> shopDtos = shopProvider.getShopByName(name)
                .stream()
                .map(shop -> ShopDto.from(shop))
                .collect(Collectors.toList());

        return new BaseResponse<>(
                GET_SHOP_LIST_BY_NAME,
                shopDtos
        );
    }

    /**
     * 해당 id 인 Shop 조회
     */
    @GetMapping("/{shopId}")
    public BaseResponse<ShopDto> getShopById(@PathVariable("shopId") Long id) {
        return new BaseResponse<>(
                GET_SHOP_BY_ID,
                ShopDto.from(
                        shopProvider.getShopById(id)
                )
        );
    }
}
