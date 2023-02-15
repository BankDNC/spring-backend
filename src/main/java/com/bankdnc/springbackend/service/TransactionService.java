package com.bankdnc.springbackend.service;

import org.springframework.http.ResponseEntity;
import reactor.core.publisher.Mono;

public interface TransactionService {
    Mono<ResponseEntity<Object>> loadAccount(String token, String accountId, Double amount, String description);
}
