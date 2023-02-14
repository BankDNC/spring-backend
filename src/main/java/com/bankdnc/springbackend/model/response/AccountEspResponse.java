package com.bankdnc.springbackend.model.response;

import com.bankdnc.springbackend.constans.TypeAccount;
import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Data
@Builder
public class AccountEspResponse {
    private String id;
    private TypeAccount typeAccount;
    private String numberAccount;
    private Double balance;
    private boolean isExempt4x1000;
    private Date dateCreation;
}
