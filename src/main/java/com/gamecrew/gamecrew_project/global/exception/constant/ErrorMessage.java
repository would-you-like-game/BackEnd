package com.gamecrew.gamecrew_project.global.exception.constant;

public class ErrorMessage {
    public static final String INVALID_EMAIL_FORMAT = "잘못된 이메일 형식입니다.";
    public static final String INVALID_EMAIL_LENGTH = "이메일은 최소 12자 이상, 최대 30자 미만으로 입력해야합니다.";
    public static final String EMAIL_NOT_BLANK = "이메일을 입력해주세요.";
    public static final String DUPLICATE_EMAIL_EXISTS = "중복된 이메일이 존재합니다.";
    public static final String FAILED_TO_SEND_EMAIL = "이메일 전송에 실패하였습니다.";
    public static final String NICKNAME_NOT_BLANK = "닉네임을 입력해주세요.";
    public static final String INVALID_NICKNAME_LENGTH = "닉네임은 최대 8자까지 입력해야합니다.";
    public static final String INVALID_NICKNAME_FORMAT = "닉네임에 특수기호를 입력할 수 없습니다.";
    public static final String DUPLICATE_NICKNAME_EXISTS = "중복된 닉네임이 존재합니다.";
    public static final String PASSWORD_NOT_BLANK = "비밀번호를 입력해주세요.";
    public static final String INVALID_PASSWORD_LENGTH = "비밀번호는 최소 8자 이상, 최대 20자 미만으로 입력해야합니다.";
    public static final String INVALID_PASSWORD_FORMAT = "잘못된 비밀번호 형식입니다. 소문자, 대문자, 특수기호를 각각 하나 이상 입력해주세요.";
    public static final String PASSWORD_MISMATCH = "비밀번호가 틀립니다.";
    public static final String CODE_MISMATCH = "이메일이나 인증 코드가 일치하지 않습니다.";
    public static final String INVALID_INPUT = "입력값을 확인해주세요.";
    public static final String NON_EXISTENT_USER = "존재하지 않는 유저입니다.";
    public static final String NOT_FOUND_USERS = "상대방 사용자를 찾을 수 없습니다.";
    public static final String DUPLICATE_CHATROOM_EXISTS = "이미 생성된 채팅방입니다.";
    public static final String CANNOT_CHOOSE_YOURSELF = "자신을 대상으로 하는 선택은 허용되지 않습니다.";


    public static final String TITLE_NOT_BLANK = "제목을 입력해주세요.";
    public static final String CONTENT_NOT_BLANK = "내용을 입력해주세요.";
    public static final String TOTALNUMBER_NOT_BLANK = "인원은 1~255명 까지 가능합니다.";
    public static final String INVALID_TITLE_LENGTH = "제목은 최소 1자 이상, 최대 20자 이하로 입력해야합니다.";
    public static final String INVALID_CONTENT_LENGTH = "내용은 최소 1자 이상, 최대 300자 이하로 입력해야합니다.";
    public static final String INVALID_TOTAL_NUMBER = "인원은 1~255명 까지 가능합니다.";
    public static final String NON_EXISTENT_POST = "해당 게시글은 존재하지 않습니다.";
}
