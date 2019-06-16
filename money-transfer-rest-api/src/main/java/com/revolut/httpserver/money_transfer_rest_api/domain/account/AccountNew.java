package com.revolut.httpserver.money_transfer_rest_api.domain.account;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class AccountNew {
	String accountName;
	Double amount;
}
