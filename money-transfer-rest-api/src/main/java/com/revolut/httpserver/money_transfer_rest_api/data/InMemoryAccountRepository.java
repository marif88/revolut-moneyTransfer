package com.revolut.httpserver.money_transfer_rest_api.data;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

import com.revolut.httpserver.money_transfer_rest_api.app.api.Constants;
import com.revolut.httpserver.money_transfer_rest_api.app.errors.ApplicationExceptions;
import com.revolut.httpserver.money_transfer_rest_api.domain.account.Account;
import com.revolut.httpserver.money_transfer_rest_api.domain.account.AccountNew;
import com.revolut.httpserver.money_transfer_rest_api.domain.account.AccountRepository;
import com.revolut.httpserver.money_transfer_rest_api.domain.account.TransferAccount;

public class InMemoryAccountRepository implements AccountRepository {

	private static Map<Integer, Account> ACCOUNT_STORE = new ConcurrentHashMap<Integer, Account>();

	@Override
	public String create(AccountNew account) {
		Integer accountNo = generateAccountNumber();
		ACCOUNT_STORE.put(accountNo, Account.builder()
				.accountNo(accountNo)
				.accountName(account.getAccountName())
				.amount(roundCurrency(account.getAmount()).toString())
				.build());
		return accountNo.toString();
	}

	@Override
	public List<Account> showAccounts() {
		if(ACCOUNT_STORE.isEmpty()) {
			throw ApplicationExceptions.notFound("No accounts found !!!").get();
		}
		return ACCOUNT_STORE.values().stream().collect(Collectors.toList());
	}

	@Override
	public Account transfer(TransferAccount transfer) {
		Account senderAccount = ACCOUNT_STORE.get(transfer.getSenderAccountNo());
		if (senderAccount != null) {
			Account receiverAccount = ACCOUNT_STORE.get(transfer.getReceiverAccountNo());
			if (receiverAccount != null) {
				BigDecimal amount = roundCurrency(transfer.getAmount());
				if(amount.compareTo(new BigDecimal(Constants.MIN_TRANSFER_AMOUNT)) >= 0) {
					if (new BigDecimal(senderAccount.getAmount()).compareTo(amount) >= 0) {
						if (senderAccount.getAccountNo().equals(receiverAccount.getAccountNo())) {
							throw ApplicationExceptions
									.invalidTransaction("Sender account and receiver account can't be same !").get();
						} else {
							senderAccount.setAmount(new BigDecimal(senderAccount.getAmount()).subtract(amount).toString());
							ACCOUNT_STORE.put(senderAccount.getAccountNo(), senderAccount);
							receiverAccount.setAmount(new BigDecimal(receiverAccount.getAmount()).add(amount).toString());
							ACCOUNT_STORE.put(receiverAccount.getAccountNo(), receiverAccount);
							return senderAccount;
						}
					} else {
						throw ApplicationExceptions.insufficientBalance("Insufficient Balance - Amount more required : "
								+ (amount.subtract(new BigDecimal(senderAccount.getAmount())))).get();
					}
				} else {
					throw ApplicationExceptions
					.invalidTransaction("Transfer amount should to be 10 or more").get();
				}
			} else {
				throw ApplicationExceptions.notFound("Receiver's account is not found !!!").get();
			}
		} else {
			throw ApplicationExceptions.notFound("Sender's account is is not found !!!").get();
		}
	}

	protected Integer generateAccountNumber() {
		if (ACCOUNT_STORE.size() > 0) {
			return Constants.ACCOUNT_NUMBER_START + ACCOUNT_STORE.size();
		} else {
			return Constants.ACCOUNT_NUMBER_START;
		}
	}
	
	protected BigDecimal roundCurrency(Double amount) {
		return new BigDecimal(amount).setScale(Constants.ROUNDING_FACTOR, RoundingMode.HALF_UP);
	}

}
