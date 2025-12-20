package com.ssafit.global.exception;

public class ErrorResponse {
	private final int status;
	private final String error;
	private final int code;
	private final String message;
	
	public ErrorResponse(ErrorCode errorCode) {
		this.status = errorCode.getStatus().value();
		this.error = errorCode.getStatus().name();
		this.code = errorCode.getCode();
		this.message = errorCode.getMessage();
	}

	public ErrorResponse(int status, String error, int code, String message) {
		this.status = status;
		this.error = error;
		this.code = code;
		this.message = message;
	}

	public int getStatus() {
		return status;
	}

	public String getError() {
		return error;
	}

	public int getCode() {
		return code;
	}

	public String getMessage() {
		return message;
	}
	
}
