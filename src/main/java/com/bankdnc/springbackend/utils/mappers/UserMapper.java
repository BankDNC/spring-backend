package com.bankdnc.springbackend.utils.mappers;

import com.bankdnc.springbackend.model.documents.Userr;
import com.bankdnc.springbackend.model.requests.UserRequest;
import com.bankdnc.springbackend.model.response.UserResponse;

public class UserMapper {

    private UserMapper() {
        throw new IllegalStateException("Utility class");
    }
    public static Userr userRequestToUser(UserRequest userRequest) {
        return Userr.builder()
                .name(userRequest.getName())
                .lastName(userRequest.getLastName())
                .email(userRequest.getEmail())
                .password(userRequest.getPassword())
                .dni(userRequest.getDni())
                .phone(userRequest.getPhone())
                .build();
    }

    public static UserResponse userToUserResponse(Userr userr) {
        return UserResponse.builder()
                .name(userr.getName())
                .lastName(userr.getLastName())
                .email(userr.getEmail())
                .dni(userr.getDni())
                .phone(userr.getPhone())
                .build();
    }
}
