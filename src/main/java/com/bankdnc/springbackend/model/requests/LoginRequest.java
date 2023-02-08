package com.bankdnc.springbackend.model.requests;

import lombok.Data;

@Data
public class LoginRequest {
    private String email;
    private String password;
}
