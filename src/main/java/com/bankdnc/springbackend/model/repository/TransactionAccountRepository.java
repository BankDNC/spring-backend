package com.bankdnc.springbackend.model.repository;


import com.bankdnc.springbackend.model.documents.TransactionAccount;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface TransactionAccountRepository extends ReactiveMongoRepository<TransactionAccount, String> {
}
