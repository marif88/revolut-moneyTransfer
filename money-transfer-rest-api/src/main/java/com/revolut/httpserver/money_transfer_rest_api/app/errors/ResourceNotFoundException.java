package com.revolut.httpserver.money_transfer_rest_api.app.errors;

public class ResourceNotFoundException extends ApplicationException{
	/**
	 * 
	 */
	private static final long serialVersionUID = -931016255646803388L;

	ResourceNotFoundException(int code, String message) {
        super(code, message);
    }
}
