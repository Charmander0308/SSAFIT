package com.ssafit.global.exception;

public class CustomException extends RuntimeException {
	private static final long serialVersionUID = 5488071593387696121L;
	
	private final ErrorCode errorCode;

	public CustomException(ErrorCode errorCode) {
		super(errorCode.getMessage());
		this.errorCode = errorCode;
	}

	public ErrorCode getErrorCode() {
		return errorCode;
	}
	
}
