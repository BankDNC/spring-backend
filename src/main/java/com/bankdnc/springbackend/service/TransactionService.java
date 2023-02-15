package com.bankdnc.springbackend.service;

import com.bankdnc.springbackend.model.response.TransactionResponse;
import org.springframework.http.ResponseEntity;
import reactor.core.publisher.Mono;

import java.util.List;

public interface TransactionService {
    Mono<ResponseEntity<Object>> loadAccount(String token, String accountId, Double amount, String description);

    Mono<ResponseEntity<List<TransactionResponse>>> history(String token, String accountId);
}
