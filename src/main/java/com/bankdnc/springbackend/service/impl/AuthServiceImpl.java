package com.bankdnc.springbackend.service.impl;

import com.bankdnc.springbackend.error.UserAlreadyExistsException;
import com.bankdnc.springbackend.model.repository.UserRepository;
import com.bankdnc.springbackend.model.requests.UserRequest;
import com.bankdnc.springbackend.model.response.UserResponse;
import com.bankdnc.springbackend.service.AuthService;
import com.bankdnc.springbackend.utils.mappers.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;

@Service
public class AuthServiceImpl implements AuthService {

    @Autowired
    private UserRepository userRepository;

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
                    return userRepository.save(UserMapper.userRequestToUser(userRequest))
                            .map(UserMapper::userToUserResponse)
                            .map(userResponse -> new ResponseEntity<>(userResponse, HttpStatus.CREATED));
                });

    }
}
