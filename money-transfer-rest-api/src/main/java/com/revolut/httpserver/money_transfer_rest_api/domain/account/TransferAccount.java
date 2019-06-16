package com.revolut.httpserver.money_transfer_rest_api.domain.account;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class TransferAccount {
	Integer senderAccountNo;
	Integer receiverAccountNo;
	Double amount;
}
