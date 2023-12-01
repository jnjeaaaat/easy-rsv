package shop.jnjeaaaat.easyrsv.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import shop.jnjeaaaat.easyrsv.domain.dto.base.BaseResponse;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    /*
     BaseException 발생 시
     BaseResponse 객체로 반환
     */
    @ExceptionHandler(BaseException.class)
    public ResponseEntity<BaseResponse> handleCustomException(
            BaseException e, HttpServletRequest request) {
        log.error("[CustomException] {} is occurred. uri:{}", e.getStatus(), request.getRequestURI());

        return ResponseEntity
                .badRequest()
                .body(new BaseResponse(e.getStatus()));
    }

    /*
    MethodArgumentNotValidException Handler
    Validation 처리를 할때 생긴 오류들을 처리한다.
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<BaseResponse> handleMethodArgumentNotValidException (
            MethodArgumentNotValidException e, HttpServletRequest request) {

        // 발생한 FiledError 들을 list 에 저장
        List<FieldError> fieldErrors = e.getBindingResult().getFieldErrors();
        FieldError fieldError = fieldErrors.get(0);   // 첫 번째 에러
        String fieldName = fieldError.getField();  // 필드명

        log.error("[MethodArgumentNotValidException] fieldName : {} ", fieldName, fieldError.getDefaultMessage() + " uri: {}", request.getRequestURI());

        return ResponseEntity
                .badRequest()
                .body(new BaseResponse(
                                HttpStatus.BAD_REQUEST,
                                fieldError.getDefaultMessage()
                        )
                );
    }

    /*
    RuntimeException Handler
    1. PathVariable, RequestParam validation 처리를 할때 생긴 오류들을 처리한다.
     */
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<BaseResponse> handleRuntimeException (
            RuntimeException e, HttpServletRequest request) {

        log.error("[RuntimeException] {} is occurred. uri:{}", e.getMessage(), request.getRequestURI());

        return ResponseEntity
                .badRequest()
                .body(new BaseResponse(
                                HttpStatus.BAD_REQUEST,
                                e.getMessage()
                        )
                );
    }
}
