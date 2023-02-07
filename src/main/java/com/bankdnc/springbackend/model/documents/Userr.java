package com.bankdnc.springbackend.model.documents;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "users")
@Data
@Builder
public class Userr {

    @Id
    private String id;
    private String name;
    private String lastName;
    private String email;
    private String password;
    private String dni;
    private String phone;
}
