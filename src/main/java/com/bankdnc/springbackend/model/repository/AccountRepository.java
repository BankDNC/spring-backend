package com.bankdnc.springbackend.model.repository;

import com.bankdnc.springbackend.model.documents.Account;
import com.bankdnc.springbackend.model.documents.User;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Mono;

public interface AccountRepository extends ReactiveMongoRepository<Account, String> {

    Mono<Long> countByUser(User user);
}
