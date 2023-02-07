package com.bankdnc.springbackend.service;

import com.bankdnc.springbackend.model.requests.UserRequest;
import com.bankdnc.springbackend.model.response.UserResponse;
import org.springframework.http.ResponseEntity;
import reactor.core.publisher.Mono;

public interface AuthService {

    Mono<ResponseEntity<UserResponse>> register(UserRequest userRequest);

}
