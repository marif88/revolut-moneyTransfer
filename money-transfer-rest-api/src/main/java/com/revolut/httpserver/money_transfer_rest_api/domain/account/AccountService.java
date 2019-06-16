package com.revolut.httpserver.money_transfer_rest_api.domain.account;

import java.util.List;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class AccountService {
	
	private final AccountRepository accountRepository;
	
	public String create(AccountNew account) {
		return accountRepository.create(account);
	}

	public List<Account> showAccounts() {
		return accountRepository.showAccounts();
	}

	public Account transfer(TransferAccount transfer) {
		return accountRepository.transfer(transfer);
	}
}
