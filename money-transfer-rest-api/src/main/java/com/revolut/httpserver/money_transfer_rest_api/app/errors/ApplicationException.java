package com.revolut.httpserver.money_transfer_rest_api.app.errors;

import lombok.Getter;

@Getter
public class ApplicationException extends RuntimeException {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 5592178577365720371L;
	
	private final int code;

    ApplicationException(int code, String message) {
        super(message);
        this.code = code;
    }
}
