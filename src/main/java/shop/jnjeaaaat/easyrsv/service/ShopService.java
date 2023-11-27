package shop.jnjeaaaat.easyrsv.service;

import shop.jnjeaaaat.easyrsv.domain.dto.shop.ShopDto;
import shop.jnjeaaaat.easyrsv.domain.dto.shop.ShopRequest;

import java.util.List;

public interface ShopService {

    ShopDto addShop(ShopRequest request);

    List<ShopDto> getShopByName(String name);

    ShopDto getShopById(Long id);
}
