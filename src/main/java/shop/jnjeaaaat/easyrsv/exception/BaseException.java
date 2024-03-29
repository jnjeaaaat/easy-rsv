package shop.jnjeaaaat.easyrsv.exception;

import lombok.*;
import shop.jnjeaaaat.easyrsv.domain.dto.base.BaseResponseStatus;

/**
 * 에러 메시지 return 에 필요한 BaseException
 * RuntimeException 을 상속한다.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BaseException extends RuntimeException{
    private BaseResponseStatus status;    // BaseResponseStatus enum 값들
    private int errorCode;      // errorCode 200, 400, ...
    private String errorMessage;    // 어떤 에러가 발생했는지 response 하기 위한 message

    public BaseException(BaseResponseStatus status) {
        this.status = status;
        this.errorCode = status.getValue();
        this.errorMessage = status.getMessage();
    }

}
