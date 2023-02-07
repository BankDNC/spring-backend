package com.bankdnc.springbackend.controllers;

import com.bankdnc.springbackend.model.requests.UserRequest;
import com.bankdnc.springbackend.model.response.UserResponse;
import com.bankdnc.springbackend.service.AuthService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import static com.bankdnc.springbackend.constans.Constant.API_V1;

@RestController
@RequestMapping(API_V1+"/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/register")
    public Mono<ResponseEntity<UserResponse>> register(@Valid @RequestBody UserRequest userRequest){
        return authService.register(userRequest);
    }

    @PostMapping
    public String login(){
        return "login";
    }

    @PostMapping("/me")
    public String me(){
        return "me";
    }
}
