package shop.jnjeaaaat.easyrsv.web;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import shop.jnjeaaaat.easyrsv.domain.dto.base.BaseResponse;
import shop.jnjeaaaat.easyrsv.domain.dto.shop.*;
import shop.jnjeaaaat.easyrsv.service.ShopService;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import java.util.List;

import static shop.jnjeaaaat.easyrsv.domain.dto.base.BaseResponseStatus.*;

@Slf4j
@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/easy-rsv/v1/shop")
public class ShopController {

    private final ShopService shopService;

    /*
     Shop 등록 Controller
     */
    @PostMapping("")
    public BaseResponse<ShopInputResponse> addShop(
            @Valid @RequestBody ShopInputRequest request) {

        log.info("[addShop] 상점 추가 시도");

        return new BaseResponse<>(
                SUCCESS_ADD_SHOP,
                shopService.addShop(request)
        );
    }

    /*
     해당 이름 (name) 의 Shop List 조회
     */
    @GetMapping("")
    public BaseResponse<List<ShopDto>> getShopListByName(
            @RequestParam @NotBlank String name) {

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

    /*
    상점 정보 수정
    수정하고자 하는 상점 id값,
    이름, 설명, 위치 값 받아서 상점 정보 수정
     */
    @PutMapping("/{shopId}")
    public BaseResponse<ShopDto> modifyShopDetails(
            @PathVariable @Min(1) Long shopId, @Valid @RequestBody ShopInputRequest request) {

        log.info("[modifyShopDetails] 상점 정보 수정 시도 - 상점 id : {}, shopId");

        return new BaseResponse<>(
                SUCCESS_MODIFY_SHOP,
                shopService.modifyShopDetails(shopId, request)
        );
    }

    /*
    상점 삭제
    삭제하려는 상점 id 값,
    해당 상점 주인유저의 회원 비밀번호, 삭제 확인 문구 입력받아서
    해당 상점 삭제
     */
    @DeleteMapping("/{shopId}")
    public BaseResponse<ShopDeleteResponse> deleteShop(
            @PathVariable @Min(1) Long shopId,
            @Valid @RequestBody ShopDeleteRequest request) {

        log.info("[deleteShop] 상점 삭제 시도");

        return new BaseResponse<>(
                SUCCESS_DELETE_SHOP,
                shopService.deleteShop(shopId, request)
        );
    }

}
