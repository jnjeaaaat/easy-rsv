package shop.jnjeaaaat.easyrsv.domain.dto.shop;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

/**
 * 상점 등록을 위한 입력값 Request Class
 */
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ShopInputRequest {
    @NotBlank(message = "상점명을 입력해주세요.")
    String name;
    @NotBlank(message = "상점에 대해 설명해주세요.")
    String description;
    @NotBlank(message = "위치를 입력해주세요.")
    String location;

    @Min(value = 1, message = "1보다 작은 값은 입력할 수 없습니다.")
    Long userId;
}
