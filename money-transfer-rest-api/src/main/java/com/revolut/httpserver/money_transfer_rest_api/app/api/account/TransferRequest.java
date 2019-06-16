package com.revolut.httpserver.money_transfer_rest_api.app.api.account;

import lombok.Data;

@Data
class TransferRequest {
	Integer senderAccountNo;
	Integer receiverAccountNo;
	Double amount;
}
