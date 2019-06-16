package com.revolut.httpserver.money_transfer_rest_api.app;

import java.net.InetSocketAddress;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.assertNotNull;

import com.sun.net.httpserver.HttpServer;

/**
 * Unit test for MoneyTransferApplication.
 */
public class MoneyTransferApplicationTest {
	
	private HttpServer server;
	
	@Before
	public void setUp() throws Exception{
		int serverPort = 8180;
		server = HttpServer.create(new InetSocketAddress(serverPort), 0);
	}
	
	@Test
	public void serverContextCheck() {
		assertNotNull(server);
	}
	
}
