package shop.jnjeaaaat.easyrsv.exception;

import lombok.*;
import shop.jnjeaaaat.easyrsv.domain.dto.base.BaseResponseStatus;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ShopException extends RuntimeException{
    private BaseResponseStatus status;
    private int errorCode;
    private String errorMessage;

    public ShopException(BaseResponseStatus status) {
        this.status = status;
        this.errorCode = status.getValue();
        this.errorMessage = status.getMessage();
    }

}
