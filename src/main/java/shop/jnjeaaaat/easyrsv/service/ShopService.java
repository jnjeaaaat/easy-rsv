package shop.jnjeaaaat.easyrsv.service;

import shop.jnjeaaaat.easyrsv.domain.dto.shop.*;

import java.util.List;

public interface ShopService {

    ShopInputResponse addShop(ShopInputRequest request);

    List<ShopDto> getShopByName(String name);

    ShopDto getShopById(Long shopId);

    ShopDto modifyShopDetails(Long shopId, ShopInputRequest request);

    ShopDeleteResponse deleteShop(Long shopId, ShopDeleteRequest request);

}
