package shop.jnjeaaaat.easyrsv.service;

import shop.jnjeaaaat.easyrsv.domain.dto.reservation.ReservationInputRequest;
import shop.jnjeaaaat.easyrsv.domain.dto.reservation.ReservationInputResponse;

public interface ReservationService {

    ReservationInputResponse reserveShop(ReservationInputRequest request);

    void cancelReservation(Long reservationId);

}
