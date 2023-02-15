package com.bankdnc.springbackend.utils.mappers;

import com.bankdnc.springbackend.model.documents.TransactionAccount;
import com.bankdnc.springbackend.model.response.TransactionResponse;

public class TransactionMapper {
    private TransactionMapper() {
        throw new IllegalStateException("Utility class");
    }

    public static TransactionResponse transactionAccountToTransactionResponse(TransactionAccount transactionAccount) {
        return TransactionResponse.builder()
                .id(transactionAccount.getId())
                .typeTransaction(transactionAccount.getTypeTransaction())
                .accountOrigin(transactionAccount.getAccountOrigin() != null ? transactionAccount.getAccountOrigin().getId() : null)
                .accountDestination(transactionAccount.getAccountDestination() != null ? transactionAccount.getAccountDestination().getId() : null)
                .amount(transactionAccount.getAmount())
                .description(transactionAccount.getDescription())
                .date(transactionAccount.getDate())
                .build();
    }
}
