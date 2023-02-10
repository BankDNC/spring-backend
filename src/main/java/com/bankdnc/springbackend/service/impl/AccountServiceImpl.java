package com.bankdnc.springbackend.service.impl;

import com.bankdnc.springbackend.constans.TypeAccount;
import com.bankdnc.springbackend.model.documents.Account;
import com.bankdnc.springbackend.model.documents.User;
import com.bankdnc.springbackend.model.repository.AccountRepository;
import com.bankdnc.springbackend.model.repository.UserRepository;
import com.bankdnc.springbackend.security.JwtService;
import com.bankdnc.springbackend.service.AccountService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;

import java.util.Date;
import java.util.UUID;

@Service
@AllArgsConstructor
public class AccountServiceImpl implements AccountService {

    private UserRepository userRepository;
    private AccountRepository accountRepository;
    private JwtService jwtService;
    @Override
    @Transactional
    public Mono<ResponseEntity> createAccount(String token) {

        Mono<String> idUser = Mono.just(jwtService.extractClaimsId(token.replace("Bearer ", "")));
        Mono<User> user = userRepository.findById(idUser);

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
}
