package com.bankdnc.springbackend.model.documents;

import com.bankdnc.springbackend.constans.TypeAccount;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DocumentReference;

import java.util.Date;

@Document(collection = "accounts")
@Data
public class Account {
    @Id
    private String id;
    private TypeAccount typeAccount;
    private String numberAccount;
    private Double balance;
    private boolean isExempt4x1000;
    private Date dateCreation;
    @DocumentReference(lazy = true)
    private User user;
}
