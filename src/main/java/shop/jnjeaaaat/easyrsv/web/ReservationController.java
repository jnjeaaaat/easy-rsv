package shop.jnjeaaaat.easyrsv.web;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import shop.jnjeaaaat.easyrsv.domain.dto.base.BaseResponse;
import shop.jnjeaaaat.easyrsv.domain.dto.reservation.ReservationInputRequest;
import shop.jnjeaaaat.easyrsv.domain.dto.reservation.ReservationInputResponse;
import shop.jnjeaaaat.easyrsv.service.ReservationService;

import javax.validation.Valid;
import javax.validation.constraints.Positive;

import static shop.jnjeaaaat.easyrsv.domain.dto.base.BaseResponseStatus.SUCCESS_ADD_RESERVATION;
import static shop.jnjeaaaat.easyrsv.domain.dto.base.BaseResponseStatus.SUCCESS_CANCEL_RESERVATION;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/easy-rsv/v1/reservation")
public class ReservationController {

    private final ReservationService reservationService;

    /*
    상점 예약
    유저 id, 상점 id, 예약 날짜 값으로 Request
     */
    @PostMapping("")
    public BaseResponse<ReservationInputResponse> reserveShop(
            @Valid @RequestBody ReservationInputRequest request) {

        log.info("[reserveShop] 상점 예약 요청 ");

        return new BaseResponse<>(
                SUCCESS_ADD_RESERVATION,
                reservationService.reserveShop(request)
        );
    }

    /*
    상점 예약 취소
    PathVariable 로 예약번호 id 입력

    별다른 Response form 없이
    상태 변경 메시지만 Response
     */
    @DeleteMapping("/{reservationId}")
    public BaseResponse cancelReservation(@PathVariable @Positive Long reservationId) {

        log.info("[cancelReservation] 예약 취소 요청");
        reservationService.cancelReservation(reservationId);

        return new BaseResponse(
                SUCCESS_CANCEL_RESERVATION
        );


    }
}
