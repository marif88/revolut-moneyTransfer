package com.revolut.httpserver.money_transfer_rest_api.app;

import java.io.IOException;
import java.net.InetSocketAddress;

import com.revolut.httpserver.money_transfer_rest_api.app.api.account.RequestHandler;
import com.sun.net.httpserver.HttpServer;

import static com.revolut.httpserver.money_transfer_rest_api.app.Configuration.getErrorHandler;
import static com.revolut.httpserver.money_transfer_rest_api.app.Configuration.getObjectMapper;
import static com.revolut.httpserver.money_transfer_rest_api.app.Configuration.getAccountService;

/**
 * Money Transfer Application
 *
 */
public class MoneyTransferApplication 
{
    public static void main( String[] args ) throws IOException {    	
        System.out.println( "Starting Money Transfer Application :)" );
        int serverPort = 8180;
        HttpServer server = HttpServer.create(new InetSocketAddress(serverPort), 0);
        
        RequestHandler registrationHandler = new RequestHandler(getAccountService(), getObjectMapper(),
                getErrorHandler());
        server.createContext("/api/account", registrationHandler::handle);
        
        server.setExecutor(null);
        server.start();
    }
}
