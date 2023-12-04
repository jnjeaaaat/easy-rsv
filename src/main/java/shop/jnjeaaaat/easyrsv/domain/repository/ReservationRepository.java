package shop.jnjeaaaat.easyrsv.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import shop.jnjeaaaat.easyrsv.domain.model.Reservation;
import shop.jnjeaaaat.easyrsv.domain.model.Shop;
import shop.jnjeaaaat.easyrsv.domain.model.User;

import java.util.List;
import java.util.Optional;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Long> {
    Optional<Reservation> findByUserAndShopAndIsFinished(User user, Shop shop, boolean isArrived);

    List<Reservation> findAllByUser(User user);

    List<Reservation> findAllByShop(Shop shop);


}
