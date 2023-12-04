package shop.jnjeaaaat.easyrsv.domain.dto.review;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

/**
 * 리뷰 수정 할때 필요한
 * Request Class
 */
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ReviewModifyRequest {

    @NotBlank
    private String description;
}
