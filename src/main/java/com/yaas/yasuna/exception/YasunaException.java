package com.yaas.yasuna.exception;

public class YasunaException extends RuntimeException{

	public YasunaException() {
		super();
	}

	public YasunaException(String message, Throwable cause) {
		super(message, cause);
	}

	public YasunaException(String message) {
		super(message);
	}

}
