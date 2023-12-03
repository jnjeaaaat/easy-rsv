package shop.jnjeaaaat.easyrsv.web;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import shop.jnjeaaaat.easyrsv.domain.dto.base.BaseResponse;
import shop.jnjeaaaat.easyrsv.domain.dto.reservation.ReservationDto;
import shop.jnjeaaaat.easyrsv.domain.dto.reservation.ReservationInputRequest;
import shop.jnjeaaaat.easyrsv.domain.dto.reservation.ReservationInputResponse;
import shop.jnjeaaaat.easyrsv.service.ReservationService;

import javax.validation.Valid;
import javax.validation.constraints.Positive;

import java.util.List;

import static shop.jnjeaaaat.easyrsv.domain.dto.base.BaseResponseStatus.*;

@Slf4j
@Validated
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
    예약 id 값 받아서
    예약한 본인이 맞는지, 예약한 상점이 맞는지 확인 후
    예약 정보 변경
     */
    @PutMapping("/{reservationId}")
    public BaseResponse<ReservationInputResponse> modifyReservation(
            @PathVariable @Positive Long reservationId,
            @Valid @RequestBody ReservationInputRequest request) {

        log.info("[modifyReservation] 예약 정보 변경 (날짜)");

        return new BaseResponse<>(
                SUCCESS_MODIFY_RESERVATION,
                reservationService.modifyReservation(reservationId, request)
        );
    }

    /*
    유저 id 값 받아서
    본인이 예약한 예약 정보 리스트 조회
     */
    @GetMapping("/user/{userId}")
    public BaseResponse<List<ReservationDto>> getMyReservations(
            @PathVariable @Positive Long userId) {

        log.info("[getMyReservations] 본인이 예약한 예약 정보 리스트 요청");

        return new BaseResponse<>(
                GET_MY_RESERVATION_LIST,
                reservationService.getMyReservations(userId)
        );
    }

    /*
    유저 id 값 받아서
    본인 상점에 대한 예약 정보 리스트 조회
     */
    @GetMapping("/shop/{shopId}")
    public BaseResponse<List<ReservationDto>> getMyShopReservations(
            @PathVariable @Positive Long shopId) {

        log.info("[getMyShopReservations] 본인 상점의 예약 정보 리스트 요청");

        return new BaseResponse<>(
                GET_SHOP_RESERVATION_LIST,
                reservationService.getMyShopReservations(shopId)
        );
    }

    /*
    예약 id 값 받아서
    본인이 예약한 정보나, 본인 상점의 예약한 정보 조회 가능
     */
    @GetMapping("/{reservationId}")
    public BaseResponse<ReservationDto> getReservation(
            @PathVariable @Positive Long reservationId) {

        log.info("[getReservation] 해당 예약 정보 요청");

        return new BaseResponse<>(
                GET_RESERVATION_BY_ID,
                reservationService.getReservation(reservationId)
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

    /*
    예약 id 값 받아서
    해당 상점의 주인이라면 예약 승인 (approve)
     */
    @PutMapping("/approve/{reservationId}")
    public BaseResponse approveReservation(@PathVariable @Positive Long reservationId) {

        log.info("[approveReservation] 예약 승인");
        reservationService.approveReservation(reservationId);

        return new BaseResponse(
                SUCCESS_APPROVE_RESERVATION
        );
    }
}
