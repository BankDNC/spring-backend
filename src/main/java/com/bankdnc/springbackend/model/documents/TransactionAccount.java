package com.bankdnc.springbackend.model.documents;

import com.bankdnc.springbackend.constans.TypeTransaction;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Document(collection = "transactionAccounts")
@Data
public class TransactionAccount {
    @Id
    private String id;
    private TypeTransaction typeTransaction;
    @DBRef
    private Account accountOrigin;
    @DBRef
    private Account accountDestination;
    private Double amount;
    private String description;
    private Date date;

}
