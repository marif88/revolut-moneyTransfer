package com.revolut.httpserver.money_transfer_rest_api.domain.account;

import java.util.List;

public interface AccountRepository {
	
	String create(AccountNew account);
	List<Account> showAccounts();
	Account transfer(TransferAccount transfer);
	
}
