package com.revolut.httpserver.money_transfer_rest_api.app.api.account;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.revolut.httpserver.money_transfer_rest_api.app.api.Constants;
import com.revolut.httpserver.money_transfer_rest_api.app.api.Handler;
import com.revolut.httpserver.money_transfer_rest_api.app.api.ResponseEntity;
import com.revolut.httpserver.money_transfer_rest_api.app.api.StatusCode;
import com.revolut.httpserver.money_transfer_rest_api.app.errors.ApplicationExceptions;
import com.revolut.httpserver.money_transfer_rest_api.app.errors.GlobalExceptionHandler;
import com.revolut.httpserver.money_transfer_rest_api.domain.account.Account;
import com.revolut.httpserver.money_transfer_rest_api.domain.account.AccountNew;
import com.revolut.httpserver.money_transfer_rest_api.domain.account.AccountService;
import com.revolut.httpserver.money_transfer_rest_api.domain.account.TransferAccount;
import com.sun.net.httpserver.HttpExchange;

public class RequestHandler extends Handler {

	private final AccountService accountService;

	public RequestHandler(AccountService accountService, ObjectMapper objectMapper,
			GlobalExceptionHandler globalExceptionHandler) {
		super(objectMapper, globalExceptionHandler);
		this.accountService = accountService;
	}

	@Override
	protected void execute(HttpExchange exchange) throws IOException {
		byte[] response;
		if ("POST".equals(exchange.getRequestMethod())) {
			ResponseEntity<RegistrationResponse> re = doPost(exchange.getRequestBody());
			exchange.getResponseHeaders().putAll(re.getHeaders());
			exchange.sendResponseHeaders(re.getStatusCode().getCode(), 0);
			response = super.writeResponse(re.getBody());
		} else if("GET".equals(exchange.getRequestMethod())) {
			ResponseEntity<ShowResponse> re = doGet();
			exchange.getResponseHeaders().putAll(re.getHeaders());
			exchange.sendResponseHeaders(re.getStatusCode().getCode(), 0);
			response = super.writeResponse(re.getBody());
		} else if("PATCH".equals(exchange.getRequestMethod())) {
			ResponseEntity<TransferResponse> re = doPatch(exchange.getRequestBody());
			exchange.getResponseHeaders().putAll(re.getHeaders());
			exchange.sendResponseHeaders(re.getStatusCode().getCode(), 0);
			response = super.writeResponse(re.getBody());
		} else {
			throw ApplicationExceptions
					.methodNotAllowed(
							"Method " + exchange.getRequestMethod() + " is not allowed for " + exchange.getRequestURI()).get();
		}
		OutputStream os = exchange.getResponseBody();
        os.write(response);
        os.close();
	}

	protected ResponseEntity<RegistrationResponse> doPost(InputStream inpStream) {
		RegistrationRequest registerRequest = super.readRequest(inpStream, RegistrationRequest.class);

        AccountNew account = AccountNew.builder()
        		.accountName(registerRequest.getAccountName())
        		.amount(registerRequest.getAmount()).build();

        String accountNo = accountService.create(account);

        RegistrationResponse response = new RegistrationResponse(accountNo);

        return new ResponseEntity<>(response,
            getHeaders(Constants.CONTENT_TYPE, Constants.APPLICATION_JSON), StatusCode.CREATED);
	}
	
	protected ResponseEntity<ShowResponse> doGet() {
		List<Account> accounts = accountService.showAccounts();
		ShowResponse response = new ShowResponse(accounts);
		return new ResponseEntity<ShowResponse>(response,
	            getHeaders(Constants.CONTENT_TYPE, Constants.APPLICATION_JSON), StatusCode.OK);

	}
	
	protected ResponseEntity<TransferResponse> doPatch(InputStream inpStream) {
		TransferRequest transferRequest = super.readRequest(inpStream, TransferRequest.class);
		
		TransferAccount transfer = TransferAccount.builder()
				.senderAccountNo(transferRequest.getSenderAccountNo())
				.receiverAccountNo(transferRequest.getReceiverAccountNo())
				.amount(transferRequest.amount)
				.build();
		
		Account sendersAccount = accountService.transfer(transfer);
		
		TransferResponse response = new TransferResponse(sendersAccount);
		
		return new ResponseEntity<TransferResponse>(response,
	            getHeaders(Constants.CONTENT_TYPE, Constants.APPLICATION_JSON), StatusCode.ACCEPTED);
	}

}
