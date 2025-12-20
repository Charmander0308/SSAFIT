package com.ssafit.global.exception;

import org.springframework.http.HttpStatus;

public enum ErrorCode {
	//1001, 1002, 1003, ..., 2001, 2002, ... 식으로 천 단위로 커스텀 에러코드를 지정
	//에러코드를 아래로 계속 추가

	// [1000 ~] : 공통 에러
	INVALID_REQUEST(HttpStatus.BAD_REQUEST, 1000, "올바르지 않은 요청입니다."),
	INVALID_INPUT_VALUE(HttpStatus.BAD_REQUEST, 1001, "입력값이 올바르지 않습니다."),
	
	// [2000 ~] : 회원 관련
	MEMBER_NOT_FOUND(HttpStatus.NOT_FOUND, 2000, "존재하지 않는 회원입니다."),
	INVALID_SELF_FOLLOW(HttpStatus.BAD_REQUEST, 2001, "자신을 팔로우할 수 없습니다."),
	ALREADY_FOLLOWING(HttpStatus.CONFLICT, 2002, "이미 팔로우 중인 회원입니다."),
	AUTH_FAILED(HttpStatus.UNAUTHORIZED, 2003, "아이디 또는 비밀번호가 일치하지 않습니다."),
	
	// [3000 ~] : 커뮤니티 관련
	COMMUNITY_NOT_FOUND(HttpStatus.NOT_FOUND, 3000, "존재하지 않는 게시글입니다."),
	
	// [4000 ~] : 댓글 관련
	
	// [5000 ~] : 유튜브 관련
	API_CONNECTION_ERROR(HttpStatus.SERVICE_UNAVAILABLE, 5000, "유튜브 서비스 연결에 실패하였습니다."),
	
	// [9000 ~] : 시스템/서버 에러
	INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, 9999, "서버 에러가 발생하였습니다. 관리자에게 문의해주세요.");
	
	private HttpStatus status;
	private final int code;
	private final String message;
	
	private ErrorCode(HttpStatus status, int code, String message) {
		this.status = status;
		this.code = code;
		this.message = message;
	}
	
	public HttpStatus getStatus() {
		return status;
	}

	public int getCode() {
		return code;
	}

	public String getMessage() {
		return message;
	}
	
}
