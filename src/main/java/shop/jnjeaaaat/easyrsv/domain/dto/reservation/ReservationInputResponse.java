package shop.jnjeaaaat.easyrsv.domain.dto.reservation;

import lombok.*;
import shop.jnjeaaaat.easyrsv.domain.dto.shop.ReservedShopInform;

import java.time.LocalDateTime;

/**
 * 상점 예약 후 받을 수 있는 Response Class
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ReservationInputResponse {
    private String email; // 예약한 유저 email
    private ReservedShopInform shop;  // 예약한 상점
    private LocalDateTime reservationDate;   // 예약날짜

    // ReservationInputResponse 에서 필요한 정보만 return
    public static ReservationInputResponse from(ReservationDto dto) {
        return ReservationInputResponse.builder()
                .email(dto.getUser().getEmail())  // 예약한 유저 email
                .shop(ReservedShopInform.builder()
                        .name(dto.getShop().getName())  // 상점 이름만
                        .location(dto.getShop().getLocation())  // 상점 위치만
                        .build())
                .reservationDate(dto.getReservationDate())   // 예약날짜
                .build();
    }
}
