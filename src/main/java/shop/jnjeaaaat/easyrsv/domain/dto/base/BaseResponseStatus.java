package shop.jnjeaaaat.easyrsv.domain.dto.base;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static org.springframework.http.HttpStatus.*;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public enum BaseResponseStatus {
    // shop
    SUCCESS_ADD_SHOP(OK.value(), "상점을 등록하였습니다."),
    GET_SHOP_LIST_BY_NAME(OK.value(), "해당 이름의 상점 리스트를 찾았습니다."),
    GET_SHOP_BY_ID(OK.value(), "상점을 조회하였습니다."),

    SUCCESS_MODIFY_SHOP(OK.value(), "상점 정보를 수정하였습니다."),

    // user
    SUCCESS_SIGN_UP(OK.value(), "회원가입 성공하였습니다."),
    SUCCESS_SIGN_IN(OK.value(), "로그인 하였습니다."),
    SUCCESS_ADD_ADMIN_AUTH(OK.value(), "관리자 계정이 되었습니다."),
    SUCCESS_ADD_PARTNER_AUTH(OK.value(), "이지랩 파트너가 되었습니다."),

    SUCCESS_MODIFY_USER(OK.value(), "유저 정보를 수정하였습니다."),


    //// Exception
    // shop
    SHOP_NOT_FOUND(BAD_REQUEST.value(), "해당 상점이 없습니다."),

    // user
    USER_NOT_FOUND(BAD_REQUEST.value(), "해당 유저가 없습니다."),
    ALREADY_REGISTERED_USER(BAD_REQUEST.value(), "이미 등록된 회원입니다."),
    PASSWORD_UN_MATCH(BAD_REQUEST.value(), "비밀번호가 다릅니다."),
    ALREADY_ADMIN_ACCOUNT(BAD_REQUEST.value(), "이미 관리자 계정입니다."),
    ALREADY_PARTNER_ACCOUNT(BAD_REQUEST.value(), "이미 이지랩 파트너 입니다."),

    // token
    EMPTY_JWT(UNAUTHORIZED.value(), "토큰을 등록해주세요."),
    INVALID_JWT(UNAUTHORIZED.value(), "인증되지 않은 토큰입니다."),
    USER_UN_MATCH(BAD_REQUEST.value(), "권한이 없는 유저입니다."),

    // access denied
    ACCESS_DENIED(FORBIDDEN.value(), "접근이 금지되었습니다."),

    ;



    private int value;
    private String message;
}
