package shop.jnjeaaaat.easyrsv.domain.dto.reservation;

import lombok.*;
import shop.jnjeaaaat.easyrsv.domain.model.Reservation;
import shop.jnjeaaaat.easyrsv.domain.model.Shop;
import shop.jnjeaaaat.easyrsv.domain.model.User;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReservationDto {

    private Long id;  // 예약 번호
    private User user;  // 예약한 유저
    private Shop shop;  // 예약한 상점
    private LocalDateTime reservationDate;  // 예약 날짜
    private boolean isApproved;  // 승인 여부

    private LocalDateTime createdAt;  // 예약을 한 시간
    private LocalDateTime updatedAt;  // 예약 정보 변경 시간

    public static ReservationDto from(Reservation reservation) {
        return ReservationDto.builder()
                .id(reservation.getId())// 예약 번호
                .user(reservation.getUser())  // 예약한 유저
                .shop(reservation.getShop())  // 예약한 상점
                .reservationDate(reservation.getReservationDate())  // 예약 날짜
                .isApproved(reservation.isApproved())  // 승인 여부
                .createdAt(reservation.getCreatedAt())  // 예약을 한 시간
                .updatedAt(reservation.getUpdatedAt())  // 예약 정보 변경 시간
                .build();
    }
}
