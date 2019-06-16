package com.revolut.httpserver.money_transfer_rest_api.data;

import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import static org.junit.Assert.assertNotNull;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.Assert.*;

import com.revolut.httpserver.money_transfer_rest_api.app.api.Constants;
import com.revolut.httpserver.money_transfer_rest_api.app.errors.InvalidRequestException;
import com.revolut.httpserver.money_transfer_rest_api.app.errors.ResourceNotFoundException;
import com.revolut.httpserver.money_transfer_rest_api.domain.account.Account;
import com.revolut.httpserver.money_transfer_rest_api.domain.account.AccountNew;
import com.revolut.httpserver.money_transfer_rest_api.domain.account.TransferAccount;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class InMemoryAccountRepositoryTest {

	private InMemoryAccountRepository inMemoryAccountRepository;
	
	@Before
	public void initialize() {
		inMemoryAccountRepository = new InMemoryAccountRepository();
	}
	
	@Test
	public void testACreate() {
		String str = inMemoryAccountRepository.create(getTestAccountNew());
		assertNotNull(str);
		assertEquals("Account Number Created", "1001", str);
	}
	
	@Test
	public void testBShowAccounts() {
		inMemoryAccountRepository.create(getTestAccountNew());
		List<Account> accounts = inMemoryAccountRepository.showAccounts();
		assertNotNull(accounts);
	}
	
	@Test
	public void testCTransferAccountsSuccess() {
		String senderAccNo = inMemoryAccountRepository.create(getTestAccountNew());
		String receiverAccNo = inMemoryAccountRepository.create(getTestAccountNew());
		TransferAccount transfer = TransferAccount
				.builder()
				.amount(50.00D)
				.senderAccountNo(Integer.parseInt(senderAccNo))
				.receiverAccountNo(Integer.parseInt(receiverAccNo))
				.build();
		Account senderAcc = inMemoryAccountRepository.transfer(transfer);
		assertNotNull(senderAcc);
		assertEquals("Remaining Balance", "950.00", senderAcc.getAmount());
	}
	
	@Test(expected = InvalidRequestException.class)
	public void testDTransferAccountInSufficentBalanceFailure() {
		String senderAccNo = inMemoryAccountRepository.create(getTestAccountNew());
		String receiverAccNo = inMemoryAccountRepository.create(getTestAccountNew());
		TransferAccount transfer = TransferAccount
				.builder()
				.amount(2500.00D)
				.senderAccountNo(Integer.parseInt(senderAccNo))
				.receiverAccountNo(Integer.parseInt(receiverAccNo))
				.build();
		inMemoryAccountRepository.transfer(transfer);
	}
	
	@Test(expected = InvalidRequestException.class)
	public void testETransferAccountMinTransferAmount() {
		String senderAccNo = inMemoryAccountRepository.create(getTestAccountNew());
		String receiverAccNo = inMemoryAccountRepository.create(getTestAccountNew());
		TransferAccount transfer = TransferAccount
				.builder()
				.amount(9.99D)
				.senderAccountNo(Integer.parseInt(senderAccNo))
				.receiverAccountNo(Integer.parseInt(receiverAccNo))
				.build();
		inMemoryAccountRepository.transfer(transfer);
	}
	
	@Test(expected = ResourceNotFoundException.class)
	public void testFTransferInvalidSender() {
		TransferAccount transfer = TransferAccount
				.builder()
				.amount(25.00D)
				.senderAccountNo(Integer.parseInt("999"))
				.receiverAccountNo(Integer.parseInt("1001"))
				.build();
		inMemoryAccountRepository.transfer(transfer);
	}
	
	@Test(expected = ResourceNotFoundException.class)
	public void testGTransferInvalidReceiver() {
		TransferAccount transfer = TransferAccount
				.builder()
				.amount(100.00D)
				.senderAccountNo(Integer.parseInt("1001"))
				.receiverAccountNo(Integer.parseInt("998"))
				.build();
		inMemoryAccountRepository.transfer(transfer);
	}
	
	@Test
	public void testHGenerateAccountNumber() {
		Integer accNo = inMemoryAccountRepository.generateAccountNumber();
		assertNotNull(accNo);
		assertEquals(true, (accNo>=Constants.ACCOUNT_NUMBER_START));
	}
	
	@Test
	public void testRoundCurrency() {
		assertEquals(new BigDecimal("100.00"), inMemoryAccountRepository.roundCurrency(100D));
		assertEquals(new BigDecimal("250.68"), inMemoryAccountRepository.roundCurrency(250.67999D));
		assertEquals(new BigDecimal("199.33"), inMemoryAccountRepository.roundCurrency(199.3333D));
		assertEquals(new BigDecimal("0.05"), inMemoryAccountRepository.roundCurrency(0.05D));
		assertEquals(new BigDecimal("399.50"), inMemoryAccountRepository.roundCurrency(399.50D));
	}

	private AccountNew getTestAccountNew() {
		AccountNew accountNew = AccountNew.builder()
				.accountName("Test Name")
				.amount(1000D)
				.build();
		return accountNew;
	}
}
