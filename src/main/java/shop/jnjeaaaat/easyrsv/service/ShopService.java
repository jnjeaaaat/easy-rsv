package shop.jnjeaaaat.easyrsv.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import shop.jnjeaaaat.easyrsv.domain.dto.ShopRequest;
import shop.jnjeaaaat.easyrsv.domain.model.Shop;
import shop.jnjeaaaat.easyrsv.domain.repository.ShopRepository;
import shop.jnjeaaaat.easyrsv.exception.ShopException;

import java.util.List;

import static shop.jnjeaaaat.easyrsv.domain.dto.base.BaseResponseStatus.*;

@Service
@RequiredArgsConstructor
public class ShopService {

    private final ShopRepository shopRepository;

    /**
     * Shop 등록 Service
     */
    public Shop addShop(ShopRequest request) {
        return shopRepository.save(
                Shop.builder()
                        .name(request.getName())
                        .description(request.getDescription())
                        .location(request.getLocation())
                        .build()
        );
    }

    /**
     * 이름(name)으로 Shop 조회
     */
    public List<Shop> getShopByName(String name) {
        return shopRepository.findAllByName(name);
    }

    /**
     * PK로 Shop 하나 조회
     */
    public Shop getShopById(Long id) {
        Shop shop = shopRepository.findById(id)
                .orElseThrow(() -> new ShopException(SHOP_NOT_FOUND));

        return shop;
    }
}
