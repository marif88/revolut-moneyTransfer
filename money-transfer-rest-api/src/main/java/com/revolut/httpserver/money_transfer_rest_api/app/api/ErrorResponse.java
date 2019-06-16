package com.revolut.httpserver.money_transfer_rest_api.app.api;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class ErrorResponse {
	int code;
    String message;
}
