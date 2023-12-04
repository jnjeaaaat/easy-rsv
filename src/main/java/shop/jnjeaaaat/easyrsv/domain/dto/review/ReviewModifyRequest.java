package shop.jnjeaaaat.easyrsv.domain.dto.review;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ReviewModifyRequest {

    @NotBlank
    private String description;
}
