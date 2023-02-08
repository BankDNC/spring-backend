package com.bankdnc.springbackend.utils.mappers;

import com.bankdnc.springbackend.model.documents.User;
import com.bankdnc.springbackend.model.requests.UserRequest;
import com.bankdnc.springbackend.model.response.UserResponse;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;

@AllArgsConstructor
public class UserMapper {
    private static PasswordEncoder passwordEncoder;

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

    public static UserResponse userToUserResponse(User user) {
        return UserResponse.builder()
                .name(user.getName())
                .lastName(user.getLastName())
                .email(user.getEmail())
                .dni(user.getDni())
                .phone(user.getPhone())
                .build();
    }
}
