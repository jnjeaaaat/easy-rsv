package shop.jnjeaaaat.easyrsv.domain.dto.reservation;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.Positive;
import java.time.LocalDateTime;

/**
 * 예약을 추가할 때 필요한 Request Class
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReservationInputRequest {

    // 예약하는 유저 id
    @Positive
    private Long userId;

    // 예약하는 상점 id
    @Positive
    private Long shopId;

    // 예약 날짜
    // 마지막 ss (초) 는 00으로 고정값
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime reservationDate;

}
