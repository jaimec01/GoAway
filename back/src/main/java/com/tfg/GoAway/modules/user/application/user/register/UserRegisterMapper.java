package com.tfg.GoAway.modules.user.application.user.register;


import org.springframework.stereotype.Component;

import com.tfg.GoAway.modules.user.domain.user.User;

@Component

public class UserRegisterMapper {

    public User toDomain(UserRegisterRecord record) {
        return User.builder()
            .email(record.getEmail())
            .name(record.getName())
            .password(record.getPassword())
            .contactNumber(record.getContactNumber())
            .direction(record.getDirection())
            .userType(record.getUserType())
            .build();
    }

    public UserRegisterResponse toResponse(User user, String message) {
        return new UserRegisterResponse(
                user.getEmail(),
                user.getName(),
                user.getContactNumber(),
                user.getDirection(),
                user.getUserType(),
                message
        );
    }

}
