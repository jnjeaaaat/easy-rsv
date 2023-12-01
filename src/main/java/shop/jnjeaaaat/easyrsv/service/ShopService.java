package shop.jnjeaaaat.easyrsv.service;

import shop.jnjeaaaat.easyrsv.domain.dto.shop.ShopDto;
import shop.jnjeaaaat.easyrsv.domain.dto.shop.ShopInputRequest;
import shop.jnjeaaaat.easyrsv.domain.dto.shop.ShopInputResponse;

import java.util.List;

public interface ShopService {

    ShopInputResponse addShop(Long userId, ShopInputRequest request);

    List<ShopDto> getShopByName(String name);

    ShopDto getShopById(Long id);
}
