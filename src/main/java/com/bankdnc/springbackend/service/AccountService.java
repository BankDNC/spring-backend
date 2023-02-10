package com.bankdnc.springbackend.service;

import org.springframework.http.ResponseEntity;
import reactor.core.publisher.Mono;

public interface AccountService {

    Mono<ResponseEntity> createAccount(String token);
}
