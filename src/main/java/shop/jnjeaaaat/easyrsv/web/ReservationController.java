package shop.jnjeaaaat.easyrsv.web;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import shop.jnjeaaaat.easyrsv.domain.dto.base.BaseResponse;
import shop.jnjeaaaat.easyrsv.domain.dto.reservation.ReservationInputRequest;
import shop.jnjeaaaat.easyrsv.domain.dto.reservation.ReservationInputResponse;
import shop.jnjeaaaat.easyrsv.service.ReservationService;

import javax.validation.Valid;

import static shop.jnjeaaaat.easyrsv.domain.dto.base.BaseResponseStatus.SUCCESS_ADD_RESERVATION;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/easy-rsv/v1/reservation")
public class ReservationController {

    private final ReservationService reservationService;

    @PostMapping("")
    public BaseResponse<ReservationInputResponse> reserveShop(
            @Valid @RequestBody ReservationInputRequest request) {

        log.info("[reserveShop] 상점 예약 요청 ");

        return new BaseResponse<>(
                SUCCESS_ADD_RESERVATION,
                reservationService.reserveShop(request)
        );
    }
}
