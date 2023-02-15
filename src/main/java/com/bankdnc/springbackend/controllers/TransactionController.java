package com.bankdnc.springbackend.controllers;

import com.bankdnc.springbackend.service.TransactionService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import static com.bankdnc.springbackend.constans.Constant.ENDPOINT_TRANSACTION;
import static com.bankdnc.springbackend.constans.Constant.LOAD_ACCOUNT;

@RestController
@RequestMapping(ENDPOINT_TRANSACTION)
@AllArgsConstructor
public class TransactionController {

    private TransactionService transactionService;
    @PostMapping(LOAD_ACCOUNT)
    public Mono<ResponseEntity<Object>> loadAccount(@RequestHeader("Authorization") String token,
                                                    @RequestParam String accountId,
                                                    @RequestParam Double amount,
                                                    @RequestParam String description) {
        return transactionService.loadAccount(token, accountId, amount, description);
    }
}
