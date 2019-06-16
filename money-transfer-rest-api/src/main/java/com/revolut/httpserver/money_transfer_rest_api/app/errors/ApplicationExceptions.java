package com.revolut.httpserver.money_transfer_rest_api.app.errors;

import java.util.function.Function;
import java.util.function.Supplier;

public class ApplicationExceptions {
	public static Function<? super Throwable, RuntimeException> invalidRequest() {
        return thr -> new InvalidRequestException(400, thr.getMessage());
    }

    public static Supplier<RuntimeException> methodNotAllowed(String message) {
        return () -> new MethodNotAllowedException(405, message);
    }

    public static Supplier<RuntimeException> notFound(String message) {
        return () -> new ResourceNotFoundException(404, message);
    }
    
    public static Supplier<RuntimeException> insufficientBalance(String message) {
        return () -> new InvalidRequestException(400, message);
    }
    
    public static Supplier<RuntimeException> invalidTransaction(String message) {
        return () -> new InvalidRequestException(400, message);
    }

}
