package shop.jnjeaaaat.easyrsv.domain.dto.base;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.OK;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public enum BaseResponseStatus {
    // shop
    SUCCESS_ADD_SHOP(OK.value(), "상점을 등록하였습니다."),
    GET_SHOP_LIST_BY_NAME(OK.value(), "해당 이름의 상점 리스트를 찾았습니다."),
    GET_SHOP_BY_ID(OK.value(), "상점을 조회하였습니다."),

    // user
    SUCCESS_SIGN_UP(OK.value(), "회원가입 성공하였습니다."),


    //// Exception
    // shop
    SHOP_NOT_FOUND(BAD_REQUEST.value(), "해당 상점이 없습니다."),

    // user
    USER_NOT_FOUND(BAD_REQUEST.value(), "해당 유저가 없습니다."),
    ALREADY_REGISTERED_USER(BAD_REQUEST.value(), "이미 등록된 회원입니다."),
    ;

    private int value;
    private String message;
}
