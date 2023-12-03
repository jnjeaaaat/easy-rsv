package shop.jnjeaaaat.easyrsv.domain.dto.reservation;

import lombok.*;
import shop.jnjeaaaat.easyrsv.domain.dto.shop.ShopSimpleInform;
import shop.jnjeaaaat.easyrsv.domain.dto.user.OwnerInform;
import shop.jnjeaaaat.easyrsv.domain.dto.user.UserSimpleInform;
import shop.jnjeaaaat.easyrsv.domain.model.Reservation;
import shop.jnjeaaaat.easyrsv.domain.model.Shop;
import shop.jnjeaaaat.easyrsv.domain.model.User;

import java.time.LocalDateTime;

/**
 * 예약 정보에 대한 Reservation Entity 를
 * 사용자에 가깝게 가공한 Dto Class
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReservationDto {

    private Long id;  // 예약 번호
    private UserSimpleInform user;  // 예약한 유저
    private ShopSimpleInform shop;  // 예약한 상점
    private LocalDateTime reservationDate;  // 예약 날짜
    private boolean isApproved;  // 승인 여부

    private LocalDateTime createdAt;  // 예약을 한 시간
    private LocalDateTime updatedAt;  // 예약 정보 변경 시간

    public static ReservationDto from(Reservation reservation) {
        Shop shop = reservation.getShop();
        User user = reservation.getUser();

        return ReservationDto.builder()
                .id(reservation.getId())  // 예약 번호
                .user(UserSimpleInform.builder()  // 예약한 유저
                        .id(user.getId())
                        .email(user.getEmail())
                        .name(user.getName())
                        .build())
                .shop(ShopSimpleInform.builder()   // 예약한 상점
                        .id(shop.getId())
                        .name(shop.getName())
                        .description(shop.getDescription())
                        .location(shop.getLocation())
                        .owner(OwnerInform.builder()
                                .id(shop.getOwner().getId())
                                .email(shop.getOwner().getEmail())
                                .name(shop.getOwner().getName())
                                .build())
                        .createdAt(shop.getCreatedAt())
                        .build())
                .reservationDate(reservation.getReservationDate())  // 예약 날짜
                .isApproved(reservation.isApproved())  // 승인 여부
                .createdAt(reservation.getCreatedAt())  // 예약을 한 시간
                .updatedAt(reservation.getUpdatedAt())  // 예약 정보 변경 시간
                .build();
    }
}
