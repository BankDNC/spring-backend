package com.bankdnc.springbackend.service.impl;

import com.bankdnc.springbackend.constans.Role;
import com.bankdnc.springbackend.error.UserAlreadyExistsException;
import com.bankdnc.springbackend.model.documents.User;
import com.bankdnc.springbackend.model.repository.UserRepository;
import com.bankdnc.springbackend.model.requests.LoginRequest;
import com.bankdnc.springbackend.model.requests.UserRequest;
import com.bankdnc.springbackend.model.response.TokenResponse;
import com.bankdnc.springbackend.model.response.UserResponse;
import com.bankdnc.springbackend.security.JwtService;
import com.bankdnc.springbackend.service.AuthService;
import com.bankdnc.springbackend.utils.mappers.UserMapper;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;

@Service
@AllArgsConstructor
public class AuthServiceImpl implements AuthService {

    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;
    private JwtService jwtService;
    private AuthenticationManager authenticationManager;

    @Transactional
    @Override
    public Mono<ResponseEntity<UserResponse>> register(UserRequest userRequest) {
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
                            .map(UserMapper::userToUserResponse)
                            .map(userResponse -> new ResponseEntity<>(userResponse, HttpStatus.CREATED));
                });

    }

    @Override
    public Mono<ResponseEntity<TokenResponse>> login(LoginRequest loginRequest) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getEmail(),
                        loginRequest.getPassword()
                )
        );

        User user = userRepository.findByEmail(loginRequest.getEmail())
                .switchIfEmpty(Mono.error(new UsernameNotFoundException("Usuario no encontrado"))).block();

        String token = jwtService.generateToken(user);

        return Mono.just(new ResponseEntity<>(new TokenResponse(token), HttpStatus.OK));

    }
}
