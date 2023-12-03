package shop.jnjeaaaat.easyrsv.service;

import shop.jnjeaaaat.easyrsv.domain.dto.reservation.ReservationDto;
import shop.jnjeaaaat.easyrsv.domain.dto.reservation.ReservationInputRequest;
import shop.jnjeaaaat.easyrsv.domain.dto.reservation.ReservationInputResponse;

import java.util.List;

public interface ReservationService {

    /**
     * 상점 예약 등록
     */
    ReservationInputResponse reserveShop(ReservationInputRequest request);

    /**
     * 예약 정보 변경 - 날짜만 변경
     */
    ReservationInputResponse modifyReservation(Long reservationId, ReservationInputRequest request);

    /**
     * 본인이 예약한 예약 리스트 return
     */
    List<ReservationDto> getMyReservations(Long userId);

    /**
     * 본인 상점에 예약된 예약 리스트 return
     */
    List<ReservationDto> getMyShopReservations(Long shopId);

    /**
     * 하나의 예약 정보 조회
     */
    ReservationDto getReservation(Long reservationId);

    /**
     * 예약 취소
     */
    void cancelReservation(Long reservationId);

    /**
     * 예약 승인 (상점 주인)
     */
    void approveReservation(Long reservationId);

}
