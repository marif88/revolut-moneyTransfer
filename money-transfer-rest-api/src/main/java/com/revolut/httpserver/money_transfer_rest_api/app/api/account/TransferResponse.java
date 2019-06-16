package com.revolut.httpserver.money_transfer_rest_api.app.api.account;

import com.revolut.httpserver.money_transfer_rest_api.domain.account.Account;
import lombok.Value;

@Value
class TransferResponse {
	Account senderAccount;
}
