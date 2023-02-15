package com.bankdnc.springbackend.service.impl;

import com.bankdnc.springbackend.constans.TypeAccount;
import com.bankdnc.springbackend.error.NoTypeAccountException;
import com.bankdnc.springbackend.model.documents.Account;
import com.bankdnc.springbackend.model.documents.User;
import com.bankdnc.springbackend.model.repository.AccountRepository;
import com.bankdnc.springbackend.model.response.AccountEspResponse;
import com.bankdnc.springbackend.model.response.AccountResponse;
import com.bankdnc.springbackend.service.AccountService;
import com.bankdnc.springbackend.service.AuthService;
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

    private AuthService authService;
    private AccountRepository accountRepository;

    @Override
    public Mono<ResponseEntity<List<AccountResponse>>> getAccounts(String token) {
        Mono<User> user = authService.getUser(token);

        return user
                .flatMapMany(u -> accountRepository.findByUser(u))
                .map(AccountMapper::accountToAccountResponse)
                .collectList()
                .map(list -> ResponseEntity.status(HttpStatus.OK).body(list))
                .defaultIfEmpty(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @Override
    @Transactional
    public Mono<ResponseEntity> createAccount(String token, String account) {
        Mono<User> user = authService.getUser(token);

        Mono<Account> accountSave = user
                .map(u -> {
                    Account newAccount = new Account();
                    TypeAccount typeAccount = null;
                    try{
                        typeAccount = TypeAccount.valueOf(account.toUpperCase());
                    }catch (Exception e){
                        throw new NoTypeAccountException("Tipo de cuenta no permitido");
                    }

                    newAccount.setTypeAccount(typeAccount);
                    newAccount.setNumberAccount(UUID.randomUUID().toString());
                    newAccount.setBalance(0.0);
                    newAccount.setDateCreation(new Date());
                    newAccount.setUser(u);
                    return newAccount;
                });

        Mono<Long> countAccount = user.flatMap(u ->
                accountRepository.countByUser(u)
        );

        return Mono.zip(accountSave, countAccount)
                .flatMap(tuple -> {
                    tuple.getT1().setExempt4x1000(tuple.getT1().getTypeAccount() == TypeAccount.AHORRO && tuple.getT2() == 0);

                    return accountRepository.save(tuple.getT1())
                            .map(newAccount ->
                                    ResponseEntity.status(HttpStatus.CREATED).build());
                });
    }

    @Override
    public Mono<ResponseEntity<AccountEspResponse>> getAccount(String token, String id) {
        Mono<User> user = authService.getUser(token);

        return user
                .flatMap(u -> getAccountById(user, id))
                .map(AccountMapper::accountToAccountEspResponse)
                .map(accountEspResponse -> ResponseEntity.status(HttpStatus.OK).body(accountEspResponse))
                .defaultIfEmpty(ResponseEntity.status(HttpStatus.NOT_FOUND).build());

    }

    @Override
    public Mono<Account> getAccountById(Mono<User> user, String id) {
        return accountRepository.findByUserAndId(user, id);
    }
}
