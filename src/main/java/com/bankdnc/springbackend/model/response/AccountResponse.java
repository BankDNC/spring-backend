package com.bankdnc.springbackend.model.response;

import com.bankdnc.springbackend.constans.TypeAccount;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AccountResponse {
    private String id;
    private TypeAccount typeAccount;
    private String numberAccount;
    private Double balance;

}
