package com.bankdnc.springbackend.controllers;

import com.bankdnc.springbackend.service.AccountService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import static com.bankdnc.springbackend.constans.Constant.CREATE_ACCOUNT;
import static com.bankdnc.springbackend.constans.Constant.ENDPOINT_ACCOUNT;

@RestController
@RequestMapping(ENDPOINT_ACCOUNT)
@AllArgsConstructor
public class AccountsController {

    private AccountService accountService;

    @PostMapping(CREATE_ACCOUNT)
    public Mono<ResponseEntity> createAccount(@RequestHeader("Authorization") String token) {
        return accountService.createAccount(token);
    }
}
