package com.bankdnc.springbackend.controllers;

import com.bankdnc.springbackend.model.response.TransactionResponse;
import com.bankdnc.springbackend.service.TransactionService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.List;

import static com.bankdnc.springbackend.constans.Constant.*;

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

    @GetMapping(HISTORY)
    public Mono<ResponseEntity<List<TransactionResponse>>> history(@RequestHeader("Authorization") String token,
                                                                   @RequestParam String accountId) {
        return transactionService.history(token, accountId);
    }

    @PostMapping(TRANSFER)
    public Mono<ResponseEntity<Object>> transfer(@RequestHeader("Authorization") String token,
                                                    @RequestParam String accountIdOrigin,
                                                    @RequestParam String accountIdDestination,
                                                    @RequestParam Double amount,
                                                    @RequestParam String description) {
        return transactionService.transfer(token, accountIdOrigin, accountIdDestination, amount, description);
    }

    @PostMapping(WITHDRAW)
    public Mono<ResponseEntity<Object>> withdraw(@RequestHeader("Authorization") String token,
                                                 @RequestParam String accountId,
                                                 @RequestParam Double amount,
                                                 @RequestParam String description) {
        return transactionService.withdraw(token, accountId, amount, description);
    }
}
