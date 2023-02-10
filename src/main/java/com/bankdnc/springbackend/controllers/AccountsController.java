package com.bankdnc.springbackend.controllers;

import com.bankdnc.springbackend.model.response.AccountResponse;
import com.bankdnc.springbackend.service.AccountService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.List;

import static com.bankdnc.springbackend.constans.Constant.CREATE_ACCOUNT;
import static com.bankdnc.springbackend.constans.Constant.ENDPOINT_ACCOUNT;

@RestController
@RequestMapping(ENDPOINT_ACCOUNT)
@AllArgsConstructor
public class AccountsController {

    private AccountService accountService;

    @GetMapping
    public Mono<ResponseEntity<List<AccountResponse>>> getAccounts(@RequestHeader("Authorization") String token) {
        return accountService.getAccounts(token);
    }

    @PostMapping(CREATE_ACCOUNT)
    public Mono<ResponseEntity> createAccount(@RequestHeader("Authorization") String token) {
        return accountService.createAccount(token);
    }
}
