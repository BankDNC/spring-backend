package com.bankdnc.springbackend.model.response;

import lombok.*;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class UserResponse {
    private String name;
    private String lastName;
    private String email;
    private String password;
    private String dni;
    private String phone;
}
