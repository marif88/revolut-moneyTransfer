package com.revolut.httpserver.money_transfer_rest_api.app;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.revolut.httpserver.money_transfer_rest_api.app.errors.GlobalExceptionHandler;
import com.revolut.httpserver.money_transfer_rest_api.data.InMemoryAccountRepository;
import com.revolut.httpserver.money_transfer_rest_api.domain.account.AccountRepository;
import com.revolut.httpserver.money_transfer_rest_api.domain.account.AccountService;

public class Configuration {
	private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();
    private static final AccountRepository ACCOUNT_REPOSITORY = new InMemoryAccountRepository();
    private static final AccountService ACCOUNT_SERVICE = new AccountService(ACCOUNT_REPOSITORY);
    private static final GlobalExceptionHandler GLOBAL_ERROR_HANDLER = new GlobalExceptionHandler(OBJECT_MAPPER);

    public static ObjectMapper getObjectMapper() {
        return OBJECT_MAPPER;
    }

    public static AccountService getAccountService() {
        return ACCOUNT_SERVICE;
    }

    static AccountRepository getAccountRepository() {
        return ACCOUNT_REPOSITORY;
    }

    public static GlobalExceptionHandler getErrorHandler() {
        return GLOBAL_ERROR_HANDLER;
    }
}
