package shop.jnjeaaaat.easyrsv.domain.dto.review;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ReviewInputRequest {

    @Positive
    private Long reservationId;

    @NotBlank
    private String description;

}
