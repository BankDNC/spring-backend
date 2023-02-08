package com.bankdnc.springbackend.controllers;

import com.bankdnc.springbackend.model.requests.LoginRequest;
import com.bankdnc.springbackend.model.requests.UserRequest;
import com.bankdnc.springbackend.model.response.TokenResponse;
import com.bankdnc.springbackend.model.response.UserResponse;
import com.bankdnc.springbackend.service.AuthService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import static com.bankdnc.springbackend.constans.Constant.*;
import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping(ENDPOINT_AUTH)
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping(REGISTER)
    public Mono<ResponseEntity<UserResponse>> register(@Valid @RequestBody UserRequest userRequest){
        return authService.register(userRequest);
    }

    @PostMapping(LOGIN)
    public Mono<ResponseEntity<TokenResponse>> login(@Valid @RequestBody LoginRequest loginRequest){
        return authService.login(loginRequest);
    }

    @GetMapping("/me")
    public Mono<ResponseEntity<String>> me(){
        return Mono.just(new ResponseEntity<>("me", OK));
    }
}
