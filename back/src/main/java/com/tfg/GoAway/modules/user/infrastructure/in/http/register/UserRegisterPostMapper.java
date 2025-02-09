package com.tfg.GoAway.modules.user.infrastructure.in.http.register;

import org.springframework.stereotype.Component;

import com.tfg.GoAway.modules.shared.password.PasswordEncoderService;
import com.tfg.GoAway.modules.user.domain.User;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class UserRegisterPostMapper {
    private final PasswordEncoderService passwordEncoderService;

    public User toDomain(UserRegisterPostRequest request) {
        return User.builder()
            .email(request.getEmail())
            .name(request.getName())
            .password(passwordEncoderService.encode(request.getPassword())) 
            .contactNumber(request.getContactNumber())
            .rol("USER")
            .build();
    }

}
