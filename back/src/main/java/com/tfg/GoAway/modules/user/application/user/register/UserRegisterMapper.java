package com.tfg.GoAway.modules.user.application.user.register;


import org.springframework.stereotype.Component;

import com.tfg.GoAway.modules.shared.password.PasswordEncoderService;
import com.tfg.GoAway.modules.user.domain.User;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class UserRegisterMapper {

    private final PasswordEncoderService passwordEncoderService;

    public User toDomain(UserRegisterRecord record, User user) {
        return User.builder()
            .email(record.getEmail())
            .name(record.getName())
            .password(passwordEncoderService.encode(record.getPassword()))
            .contactNumber(record.getContactNumber())
            .direction(user.getDirection())
            .userType(user.getUserType())
            .build();
    } 

    public UserRegisterResponse toResponse(User user, String message) {
        return new UserRegisterResponse(
                user.getEmail(),
                user.getName(),
                user.getContactNumber(),
                message
        );
    }

}
