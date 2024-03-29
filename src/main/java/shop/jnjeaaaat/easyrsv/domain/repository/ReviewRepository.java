package shop.jnjeaaaat.easyrsv.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import shop.jnjeaaaat.easyrsv.domain.model.Reservation;
import shop.jnjeaaaat.easyrsv.domain.model.Review;
import shop.jnjeaaaat.easyrsv.domain.model.Shop;

import java.util.List;
import java.util.Optional;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {
    Optional<Review> findByReservation(Reservation reservation);

    List<Review> findAllByShop(Shop shop);
}
