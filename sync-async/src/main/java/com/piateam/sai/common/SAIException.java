package com.piateam.sai.common;

public class SAIException extends RuntimeException {
	public SAIException(String message, Throwable cause) {
		super(message, cause);
	}

	public SAIException(String message) {
		super(message);
	}
}
