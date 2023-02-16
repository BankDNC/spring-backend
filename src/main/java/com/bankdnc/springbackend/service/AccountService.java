package com.bankdnc.springbackend.service;

import com.bankdnc.springbackend.model.documents.Account;
import com.bankdnc.springbackend.model.documents.User;
import com.bankdnc.springbackend.model.response.AccountEspResponse;
import com.bankdnc.springbackend.model.response.AccountResponse;
import org.springframework.http.ResponseEntity;
import reactor.core.publisher.Mono;

import java.util.List;

public interface AccountService {
    Mono<ResponseEntity<List<AccountResponse>>> getAccounts(String token);
    Mono<ResponseEntity> createAccount(String token, String account);

    Mono<ResponseEntity<AccountEspResponse>> getAccount(String token, String id);

    Mono<Account> getAccountById(Mono<User> user, String id);
    Mono<Account> getAccountById(String id);

    Mono<ResponseEntity<Object>> deleteAccount(String token, String id);
}
