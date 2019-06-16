package com.revolut.httpserver.money_transfer_rest_api.app.api;

import org.junit.Before;
import org.junit.Test;

import com.revolut.httpserver.money_transfer_rest_api.app.Configuration;
import com.revolut.httpserver.money_transfer_rest_api.app.api.account.RequestHandler;

public class RequestHandlerTest {
	
	private RequestHandler requestHandler;
	
	@Before
	public void initialize() {
		requestHandler = new RequestHandler(Configuration.getAccountService(), 
				Configuration.getObjectMapper(), Configuration.getErrorHandler());
	}
	
	@Test
	public void testExecute() {
		
	}

}
