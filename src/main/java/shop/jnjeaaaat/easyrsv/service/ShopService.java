package shop.jnjeaaaat.easyrsv.service;

import shop.jnjeaaaat.easyrsv.domain.dto.shop.ShopDto;
import shop.jnjeaaaat.easyrsv.domain.dto.shop.ShopInputRequest;
import shop.jnjeaaaat.easyrsv.domain.dto.shop.ShopInputResponse;

import java.util.List;

public interface ShopService {

    ShopInputResponse addShop(ShopInputRequest request);

    List<ShopDto> getShopByName(String name);

    ShopDto getShopById(Long shopId);

    ShopDto modifyShopDetails(Long shopId, ShopInputRequest request);
}
