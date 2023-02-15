package com.bankdnc.springbackend.model.repository;


import com.bankdnc.springbackend.model.documents.Account;
import com.bankdnc.springbackend.model.documents.TransactionAccount;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Flux;

public interface TransactionAccountRepository extends ReactiveMongoRepository<TransactionAccount, String> {
    Flux<TransactionAccount> findByAccountOriginOrAccountDestination(Account accountOrigin ,Account accountDestination);
}
