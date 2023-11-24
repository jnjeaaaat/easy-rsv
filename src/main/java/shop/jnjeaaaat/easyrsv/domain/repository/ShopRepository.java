package shop.jnjeaaaat.easyrsv.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import shop.jnjeaaaat.easyrsv.domain.model.Shop;

import java.util.List;

@Repository
public interface ShopRepository extends JpaRepository<Shop, Long> {
    List<Shop> findAllByName(String name);
}
