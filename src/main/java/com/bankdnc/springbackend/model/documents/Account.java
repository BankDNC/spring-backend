package com.bankdnc.springbackend.model.documents;

import com.bankdnc.springbackend.constans.TypeAccount;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Document(collection = "accounts")
@Data
public class Account {
    @Id
    private String id;
    private TypeAccount typeAccount;
    private Double balance;
    private boolean isExempt4x1000;
    private Date dateCreation;
    @DBRef(lazy = true)
    private User user;
}
