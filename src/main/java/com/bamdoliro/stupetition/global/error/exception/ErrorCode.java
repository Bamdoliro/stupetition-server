package com.bamdoliro.stupetition.global.error.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorCode {
    INTERNAL_SERVER_ERROR(500, "서버에 오류가 발생했습니다."),
    BAD_REQUEST(400, "잘못된 요청입니다."),

    EXPIRED_TOKEN(401, "만료된 토큰입니다."),
    INVALID_TOKEN(401, "유효하지 않은 토큰입니다."),

    USER_NOT_FOUND(404, "사용자를 찾을 수 없습니다."),
    USER_ALREADY_EXISTS(422, "사용자가 이미 존재합니다."),
    PASSWORD_MISMATCH(401, "비밀번호가 틀렸습니다."),
    USER_NOT_LOGIN(401, "로그인 해야 합니다."),

    SCHOOL_NOT_FOUND(404, "학교를 찾을 수 없습니다."),

    BOARD_NOT_FOUND(404, "글을 찾을 수 없습니다."),
    USER_IS_NOT_WRITER(401, "글의 작성자가 아닙니다."),
    ALREADY_APPROVE(422, "이미 동의했습니다."),
    ALREADY_ANSWER(422, "이미 답변했습니다."),
    SAME_BOARD_WRITER_AND_AGREER(422, "본인의 글에 동의할 수 없습니다."),
    STATUS_MISMATCH(401, "Status 가 맞지 않습니다.")
    ;


    private final int status;
    private final String message;
}
