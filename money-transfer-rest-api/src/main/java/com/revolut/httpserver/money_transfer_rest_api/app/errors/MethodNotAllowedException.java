package com.revolut.httpserver.money_transfer_rest_api.app.errors;

public class MethodNotAllowedException extends ApplicationException{
	/**
	 * 
	 */
	private static final long serialVersionUID = -2958346944123653313L;

	MethodNotAllowedException(int code, String message) {
        super(code, message);
    }
}
