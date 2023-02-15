package com.bankdnc.springbackend.utils.mappers;

import com.bankdnc.springbackend.model.documents.Account;
import com.bankdnc.springbackend.model.response.AccountEspResponse;
import com.bankdnc.springbackend.model.response.AccountResponse;

public class AccountMapper {

    private AccountMapper() {
        throw new IllegalStateException("Utility class");
    }

    public static AccountResponse accountToAccountResponse(Account account) {
        return AccountResponse.builder()
                .id(account.getId())
                .typeAccount(account.getTypeAccount())
                .balance(account.getBalance())
                .build();
    }

    public static AccountEspResponse accountToAccountEspResponse(Account account) {
        return AccountEspResponse.builder()
                .id(account.getId())
                .typeAccount(account.getTypeAccount())
                .balance(account.getBalance())
                .isExempt4x1000(account.isExempt4x1000())
                .dateCreation(account.getDateCreation())
                .build();
    }
}
