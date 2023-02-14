package com.bankdnc.springbackend.service;

import com.bankdnc.springbackend.model.response.AccountResponse;
import org.springframework.http.ResponseEntity;
import reactor.core.publisher.Mono;

import java.util.List;

public interface AccountService {
    Mono<ResponseEntity<List<AccountResponse>>> getAccounts(String token);
    Mono<ResponseEntity> createAccount(String token, String account);
}
