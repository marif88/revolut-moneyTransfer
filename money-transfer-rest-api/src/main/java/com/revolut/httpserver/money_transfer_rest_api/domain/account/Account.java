package com.revolut.httpserver.money_transfer_rest_api.domain.account;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Account {
	Integer accountNo;
	String accountName;
	String amount;
}
