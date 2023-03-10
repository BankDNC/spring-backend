package com.bankdnc.springbackend.service.impl;

import com.bankdnc.springbackend.constans.Role;
import com.bankdnc.springbackend.error.UserAlreadyExistsException;
import com.bankdnc.springbackend.model.documents.User;
import com.bankdnc.springbackend.model.repository.UserRepository;
import com.bankdnc.springbackend.model.requests.LoginRequest;
import com.bankdnc.springbackend.model.requests.UserRequest;
import com.bankdnc.springbackend.model.response.TokenResponse;
import com.bankdnc.springbackend.security.JwtService;
import com.bankdnc.springbackend.service.AuthService;
import com.bankdnc.springbackend.utils.mappers.UserMapper;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.ReactiveUserDetailsService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;

import java.util.HashMap;
import java.util.Map;

@Service
@AllArgsConstructor
public class AuthServiceImpl implements AuthService {

    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;
    private JwtService jwtService;
    private ReactiveUserDetailsService userDetailsService;

    @Transactional
    @Override
    public Mono<ResponseEntity<TokenResponse>> register(UserRequest userRequest) {
        Mono<Boolean> existsEmail = userRepository.existsByEmail(userRequest.getEmail());
        Mono<Boolean> existsDni = userRepository.existsByDni(userRequest.getDni());
        return Mono.zip(existsEmail, existsDni)
                .flatMap(tuple -> {
                    if (tuple.getT1() || tuple.getT2()) {
                        return Mono.error(new UserAlreadyExistsException("Correo o DNI ya existen"));
                    }

                    User user = UserMapper.userRequestToUser(userRequest);
                    user.setPassword(passwordEncoder.encode(userRequest.getPassword()));
                    user.setRole(Role.USER);

                    return userRepository.save(user)
                            .map(userDetails ->{
                                Map<String, Object> claims = new HashMap<>();
                                claims.put("id", userDetails.getId());
                                claims.put("name", userDetails.getName());
                                claims.put("lastName", userDetails.getLastName());
                                claims.put("email", userDetails.getEmail());

                                return new ResponseEntity<>(new TokenResponse(jwtService.generateToken(userDetails, claims)), HttpStatus.CREATED);
                            });
                });

    }

    @Override
    @Transactional(readOnly = true)
    public Mono<ResponseEntity<TokenResponse>> login(LoginRequest loginRequest) {
        Mono<UserDetails> foundUser = userDetailsService.findByUsername(loginRequest.getEmail());

        return foundUser.
                flatMap(userDetails -> {
                    if (passwordEncoder.matches(loginRequest.getPassword(), userDetails.getPassword())) {
                        if(userDetails instanceof User user){
                            Map<String, Object> claims = new HashMap<>();
                            claims.put("id", user.getId());
                            claims.put("name", user.getName());
                            claims.put("lastName", user.getLastName());
                            claims.put("email", user.getEmail());
                            return Mono.just(ResponseEntity.ok(new TokenResponse(jwtService.generateToken(userDetails, claims)))
                            );
                        }
                        return Mono.just(ResponseEntity.ok(new TokenResponse(jwtService.generateToken(userDetails)))
                        );
                    }
                    return Mono.just(ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new TokenResponse()));
                })
                .defaultIfEmpty(ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new TokenResponse()));

    }

    @Override
    public Mono<User> getUser(String token) {
        return Mono.just(jwtService.extractClaimsId(token.replace("Bearer ", "")))
                .flatMap(id -> userRepository.findById(id));
    }
}
