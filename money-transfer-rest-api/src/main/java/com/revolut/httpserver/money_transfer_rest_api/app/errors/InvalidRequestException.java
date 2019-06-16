package com.revolut.httpserver.money_transfer_rest_api.app.errors;

public class InvalidRequestException extends ApplicationException{
	/**
	 * 
	 */
	private static final long serialVersionUID = -3887449763962883013L;

	public InvalidRequestException(int code, String message) {
        super(code, message);
    }
}
