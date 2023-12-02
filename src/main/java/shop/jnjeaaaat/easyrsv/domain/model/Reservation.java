package shop.jnjeaaaat.easyrsv.domain.model;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
@Entity
public class Reservation extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 유저 한명이 여러개의 예약을 등록할 수 있음
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id")
    private User user;

    // 한 상점당 여러개의 예약이 등록될 수 있음
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "shop_id")
    private Shop shop;

    // 예약 날짜
    @Column(nullable = false)
    private LocalDateTime reservationDate;

    @Column(nullable = false)
    private boolean isApproved;

}
