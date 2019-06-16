package com.revolut.httpserver.money_transfer_rest_api.app.api.account;

import java.util.List;
import com.revolut.httpserver.money_transfer_rest_api.domain.account.Account;
import lombok.Value;

@Value
class ShowResponse {
	List<Account> accounts;
}
