package com.bankdnc.springbackend.model.repository;

import com.bankdnc.springbackend.model.documents.User;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public interface UserRepository extends ReactiveCrudRepository<User, String> {
    Mono<Boolean> existsByEmail(String email);

    Mono<Boolean> existsByDni(String dni);
    Mono<User> findByEmail(String email);
}
