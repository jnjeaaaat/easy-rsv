package shop.jnjeaaaat.easyrsv.service;

import shop.jnjeaaaat.easyrsv.domain.dto.shop.*;

import java.util.List;

/**
 * ShopService interface
 */
public interface ShopService {

    /**
     * 상점 등록
     */
    ShopInputResponse addShop(ShopInputRequest request);

    /**
     * 이름으로 상점 리스트 조회
     */
    List<ShopDto> getShopByName(String name);

    /**
     * 상점 하나 조회
     */
    ShopDto getShopById(Long shopId);

    /**
     * 상점 정보 수정
     */
    ShopDto modifyShopDetails(Long shopId, ShopInputRequest request);

    /**
     * 상점 삭제
     */
    ShopDeleteResponse deleteShop(Long shopId, ShopDeleteRequest request);

}
