package shop.jnjeaaaat.easyrsv.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import shop.jnjeaaaat.easyrsv.domain.model.Shop;
import shop.jnjeaaaat.easyrsv.domain.repository.ShopRepository;
import shop.jnjeaaaat.easyrsv.exception.BaseException;

import java.util.List;

import static shop.jnjeaaaat.easyrsv.domain.dto.base.BaseResponseStatus.SHOP_NOT_FOUND;

@Service
@RequiredArgsConstructor
public class ShopProvider {
    private final ShopRepository shopRepository;

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
        return shopRepository.findById(id)
                .orElseThrow(() -> new BaseException(SHOP_NOT_FOUND));
    }
}
