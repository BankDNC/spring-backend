package com.bankdnc.springbackend.service;

import com.bankdnc.springbackend.model.documents.User;
import com.bankdnc.springbackend.model.requests.LoginRequest;
import com.bankdnc.springbackend.model.requests.UserRequest;
import com.bankdnc.springbackend.model.response.TokenResponse;
import org.springframework.http.ResponseEntity;
import reactor.core.publisher.Mono;

public interface AuthService {

    Mono<ResponseEntity<TokenResponse>> register(UserRequest userRequest);

    Mono<ResponseEntity<TokenResponse>> login(LoginRequest loginRequest);

    Mono<User> getUser(String token);
}
