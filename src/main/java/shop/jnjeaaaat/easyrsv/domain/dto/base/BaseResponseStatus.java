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
    SUCCESS_DELETE_SHOP(OK.value(), "해당 상점을 삭제하였습니다."),

    // user
    SUCCESS_SIGN_UP(OK.value(), "회원가입 성공하였습니다."),
    SUCCESS_SIGN_IN(OK.value(), "로그인 하였습니다."),
    SUCCESS_ADD_ADMIN_AUTH(OK.value(), "관리자 계정이 되었습니다."),
    SUCCESS_ADD_PARTNER_AUTH(OK.value(), "이지랩 파트너가 되었습니다."),

    SUCCESS_MODIFY_USER(OK.value(), "유저 정보를 수정하였습니다."),

    // reservation
    SUCCESS_ADD_RESERVATION(OK.value(), "상점을 예약하였습니다."),
    SUCCESS_CANCEL_RESERVATION(OK.value(), "상점 예약을 취소하였습니다."),
    SUCCESS_MODIFY_RESERVATION(OK.value(), "예약 정보를 변경하였습니다."),
    GET_MY_RESERVATION_LIST(OK.value(), "나의 예약 정보 리스트를 조회하였습니다."),
    GET_SHOP_RESERVATION_LIST(OK.value(), "상점 예약 리스트를 조회하였습니다."),
    GET_RESERVATION_BY_ID(OK.value(), "예약 정보를 조회하였습니다."),

    SUCCESS_APPROVE_RESERVATION(OK.value(), "예약을 승인하였습니다."),
    SUCCESS_DENY_RESERVATION(OK.value(), "예약을 거절하였습니다."),
    SUCCESS_ARRIVE_IN_TIME(OK.value(), "상점 도착 확인되었습니다."),

    // review
    SUCCESS_WRITE_REVIEW(OK.value(), "새로운 리뷰를 작성하였습니다."),
    SUCCESS_MODIFY_REVIEW(OK.value(), "리뷰 내용을 수정하였습니다."),
    SUCCESS_GET_SHOP_REVIEW_LIST(OK.value(), "해당 상점의 리뷰 리스트를 조회하였습니다."),
    SUCCESS_GET_REVIEW(OK.value(), "리뷰를 조회하였습니다."),

    ///////////////////////////////// Exception ////////////////////////////////
    // shop
    SHOP_NOT_FOUND(BAD_REQUEST.value(), "해당 상점이 없습니다."),
    CHECK_MESSAGE_UN_MATCH(BAD_REQUEST.value(), "삭제 확인 문구가 잘못되었습니다."),
    NOT_OWNER(BAD_REQUEST.value(), "해당 상점의 주인이 아닙니다."),

    // user
    USER_NOT_FOUND(BAD_REQUEST.value(), "해당 유저가 없습니다."),
    ALREADY_REGISTERED_USER(BAD_REQUEST.value(), "이미 등록된 회원입니다."),
    PASSWORD_UN_MATCH(BAD_REQUEST.value(), "비밀번호가 다릅니다."),
    ALREADY_ADMIN_ACCOUNT(BAD_REQUEST.value(), "이미 관리자 계정입니다."),
    ALREADY_PARTNER_ACCOUNT(BAD_REQUEST.value(), "이미 이지랩 파트너 입니다."),

    // reserve
    OWNER_CANT_RESERVE(BAD_REQUEST.value(), "본인 상점은 예약할 수 없습니다."),
    ALREADY_RESERVED(BAD_REQUEST.value(), "이미 예약한 상점입니다. 기존 예약 삭제 후 진행해주세요."),
    RESERVATION_NOT_FOUND(BAD_REQUEST.value(), "예약된 내역이 없습니다."),
    MODIFY_JUST_ME(BAD_REQUEST.value(), "본인만 예약을 변경할 수 있습니다."),
    MODIFY_JUST_THAT_SHOP(BAD_REQUEST.value(), "예약한 상점이 아닙니다."),
    NO_AUTH_TO_BROWSE(BAD_REQUEST.value(), "예약 정보를 조회할 권한이 없습니다."),
    APPROVE_JUST_OWNER(BAD_REQUEST.value(), "상점 주인만 승인할 수 있습니다."),
    ALREADY_APPROVED(BAD_REQUEST.value(), "이미 승인된 예약입니다."),
    NOT_APPROVED(BAD_REQUEST.value(), "승인되지 않은 예약정보 입니다."),
    ALREADY_ARRIVED(BAD_REQUEST.value(), "도착 확인된 예약입니다."),
    ALREADY_FINISHED(BAD_REQUEST.value(), "이미 완료된 예약입니다."),
    YOU_ARE_LATE(BAD_REQUEST.value(), "10분전에 도착해야 예약 확인 할 수 있습니다."),

    // review
    TOO_EARLY_REVIEW(BAD_REQUEST.value(), "리뷰를 쓰기엔 이릅니다."),
    ALREADY_WRITE_REVIEW(BAD_REQUEST.value(), "이미 리뷰를 작성하였습니다."),
    REVIEW_NOT_FOUND(BAD_REQUEST.value(), "해당 리뷰가 없습니다."),

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
