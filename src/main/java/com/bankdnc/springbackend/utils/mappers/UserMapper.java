package com.bankdnc.springbackend.utils.mappers;

import com.bankdnc.springbackend.model.documents.User;
import com.bankdnc.springbackend.model.requests.UserRequest;

public class UserMapper {

    private UserMapper() {
        throw new IllegalStateException("Utility class");
    }

    public static User userRequestToUser(UserRequest userRequest) {
        return User.builder()
                .name(userRequest.getName())
                .lastName(userRequest.getLastName())
                .email(userRequest.getEmail())
                .password(userRequest.getPassword())
                .dni(userRequest.getDni())
                .phone(userRequest.getPhone())
                .build();
    }
}
