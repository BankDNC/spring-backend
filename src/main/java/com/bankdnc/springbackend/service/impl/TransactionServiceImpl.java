package com.bankdnc.springbackend.service.impl;

import com.bankdnc.springbackend.constans.TypeTransaction;
import com.bankdnc.springbackend.model.documents.Account;
import com.bankdnc.springbackend.model.documents.TransactionAccount;
import com.bankdnc.springbackend.model.documents.User;
import com.bankdnc.springbackend.model.repository.AccountRepository;
import com.bankdnc.springbackend.model.repository.TransactionAccountRepository;
import com.bankdnc.springbackend.model.response.TransactionResponse;
import com.bankdnc.springbackend.service.AccountService;
import com.bankdnc.springbackend.service.AuthService;
import com.bankdnc.springbackend.service.TransactionService;
import com.bankdnc.springbackend.utils.mappers.TransactionMapper;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;

import java.util.Date;
import java.util.List;

@Service
@AllArgsConstructor
public class TransactionServiceImpl implements TransactionService {

    private AuthService authService;
    private AccountService accountService;
    private AccountRepository accountRepository;
    private TransactionAccountRepository transactionAccountRepository;
    @Override
    @Transactional
    public Mono<ResponseEntity<Object>> loadAccount(String token, String accountId, Double amount, String description) {
        Mono<User> user = authService.getUser(token);
        Mono<Account> account = accountService.getAccountById(user, accountId);

        return account.flatMap(a -> {
                    a.setBalance(a.getBalance() + amount);
                    return accountRepository.save(a);
                })
                .flatMap(a -> {
                    TransactionAccount transactionAccount = new TransactionAccount();
                    transactionAccount.setTypeTransaction(TypeTransaction.DEPOSITO);
                    transactionAccount.setAmount(amount);
                    transactionAccount.setAccountDestination(a);
                    transactionAccount.setDescription(description);
                    transactionAccount.setDate(new Date());
                    return transactionAccountRepository.save(transactionAccount);
                })
                .map(t -> ResponseEntity.ok().build())
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @Override
    public Mono<ResponseEntity<List<TransactionResponse>>> history(String token, String accountId) {
        Mono<User> user = authService.getUser(token);
        Mono<Account> account = accountService.getAccountById(user, accountId);

        return account
                .flatMapMany(a ->
                     transactionAccountRepository.findByAccountOriginOrAccountDestination(a,a)
                )
                .map(TransactionMapper::transactionAccountToTransactionResponse)
                .collectList()
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }
}
