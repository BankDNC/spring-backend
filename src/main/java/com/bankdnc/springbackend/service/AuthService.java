package com.bankdnc.springbackend.service;

import com.bankdnc.springbackend.model.requests.LoginRequest;
import com.bankdnc.springbackend.model.requests.UserRequest;
import com.bankdnc.springbackend.model.response.TokenResponse;
import com.bankdnc.springbackend.model.response.UserResponse;
import org.springframework.http.ResponseEntity;
import reactor.core.publisher.Mono;

public interface AuthService {

    Mono<ResponseEntity<UserResponse>> register(UserRequest userRequest);

    Mono<ResponseEntity<TokenResponse>> login(LoginRequest loginRequest);
}
