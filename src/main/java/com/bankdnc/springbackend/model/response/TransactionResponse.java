package com.bankdnc.springbackend.model.response;

import com.bankdnc.springbackend.constans.TypeTransaction;
import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Data
@Builder
public class TransactionResponse {

    private String id;
    private TypeTransaction typeTransaction;
    private String accountOrigin;
    private String accountDestination;
    private Double amount;
    private String description;
    private Date date;
}
