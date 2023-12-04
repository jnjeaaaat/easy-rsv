package shop.jnjeaaaat.easyrsv.domain.dto.review;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;

/**
 * 리뷰 작성 시에 필요한 Request Class
 */
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ReviewInputRequest {

    @Positive
    private Long reservationId;

    @NotBlank
    private String description;

}
