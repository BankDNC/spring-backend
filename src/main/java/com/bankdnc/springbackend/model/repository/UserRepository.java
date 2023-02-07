package com.bankdnc.springbackend.model.repository;

import com.bankdnc.springbackend.model.documents.Userr;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Mono;

public interface UserRepository extends ReactiveCrudRepository<Userr, String> {
    Mono<Boolean> existsByEmail(String email);

    Mono<Boolean> existsByDni(String dni);
}
