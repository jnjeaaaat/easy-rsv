package shop.jnjeaaaat.easyrsv.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import shop.jnjeaaaat.easyrsv.domain.dto.base.BaseResponse;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * BaseException 발생 시
     * BaseResponse 객체로 반환
     */
    @ExceptionHandler(BaseException.class)
    public ResponseEntity<BaseResponse> handleShopException(BaseException e) {
        log.error("{} is occurred.", e.getStatus());

        return ResponseEntity
                .badRequest()
                .body(new BaseResponse(e.getStatus()));
    }
}
