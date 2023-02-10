package com.bankdnc.springbackend.utils.mappers;

import com.bankdnc.springbackend.model.documents.Account;
import com.bankdnc.springbackend.model.response.AccountResponse;

public class AccountMapper {

    private AccountMapper() {
        throw new IllegalStateException("Utility class");
    }

    public static AccountResponse accountToAccountResponse(Account account) {
        return AccountResponse.builder()
                .id(account.getId())
                .typeAccount(account.getTypeAccount())
                .numberAccount(account.getNumberAccount())
                .balance(account.getBalance())
                .build();
    }
}
