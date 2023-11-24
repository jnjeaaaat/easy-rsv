package shop.jnjeaaaat.easyrsv.domain.dto.base;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Getter;

@Getter
@JsonPropertyOrder({"message", "result"})
public class BaseResponse<T> {
    private final int code;
    private final String message;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private T result;

    // error response
    public BaseResponse(BaseResponseStatus status) {
        this.code = status.getValue();
        this.message = status.getMessage();
    }

    // 성공 케이스 response
    public BaseResponse(BaseResponseStatus status, T result) {
        this.code = status.getValue();
        this.message = status.getMessage();
        this.result = result;
    }
}
