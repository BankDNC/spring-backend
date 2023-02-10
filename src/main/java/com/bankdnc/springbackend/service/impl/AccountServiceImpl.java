package com.bankdnc.springbackend.service.impl;

import com.bankdnc.springbackend.constans.TypeAccount;
import com.bankdnc.springbackend.model.documents.Account;
import com.bankdnc.springbackend.model.documents.User;
import com.bankdnc.springbackend.model.repository.AccountRepository;
import com.bankdnc.springbackend.model.repository.UserRepository;
import com.bankdnc.springbackend.model.response.AccountResponse;
import com.bankdnc.springbackend.security.JwtService;
import com.bankdnc.springbackend.service.AccountService;
import com.bankdnc.springbackend.utils.mappers.AccountMapper;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class AccountServiceImpl implements AccountService {

    private UserRepository userRepository;
    private AccountRepository accountRepository;
    private JwtService jwtService;

    @Override
    public Mono<ResponseEntity<List<AccountResponse>>> getAccounts(String token) {
        Mono<User> user = getUser(token);

        return user
                .flatMapMany(u -> accountRepository.findByUser(u))
                .map(AccountMapper::accountToAccountResponse)
                .collectList()
                .map(list -> ResponseEntity.status(HttpStatus.OK).body(list))
                .defaultIfEmpty(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @Override
    @Transactional
    public Mono<ResponseEntity> createAccount(String token) {
        Mono<User> user = getUser(token);

        Mono<Account> accountSave = user
                .map(u -> {
            Account account = new Account();
            account.setTypeAccount(TypeAccount.AHORRO);
            account.setNumberAccount(UUID.randomUUID().toString());
            account.setBalance(0.0);
            account.setDateCreation(new Date());
            account.setUser(u);
            return account;
        });

        Mono<Long> countAccount = user.flatMap(u ->
            accountRepository.countByUser(u)
        );

        return Mono.zip(accountSave, countAccount)
                .flatMap(tuple -> {
                    tuple.getT1().setExempt4x1000(tuple.getT2() == 0);

                    return accountRepository.save(tuple.getT1())
                            .map(account ->
                                 ResponseEntity.status(HttpStatus.CREATED).build());
                });
    }

    private Mono<User> getUser(String token) {
        return Mono.just(jwtService.extractClaimsId(token.replace("Bearer ", "")))
                .flatMap(id -> userRepository.findById(id));
    }
}
