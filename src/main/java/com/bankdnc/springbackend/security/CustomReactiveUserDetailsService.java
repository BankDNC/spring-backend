package com.bankdnc.springbackend.security;

import com.bankdnc.springbackend.model.documents.User;
import com.bankdnc.springbackend.model.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.ReactiveUserDetailsService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class CustomReactiveUserDetailsService implements ReactiveUserDetailsService {

    private final UserRepository userRepository;

    @Override
    public Mono<UserDetails> findByUsername(String username) {
        return userRepository.findByEmail(username)
                .cast(UserDetails.class)
                .switchIfEmpty(Mono.just(User.builder()
                        .email("")
                        .password("")
                        .build()));
    }

}
