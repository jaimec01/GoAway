package com.tfg.GoAway.modules.user.infraestructure.in.http.register;

import org.springframework.stereotype.Component;

import com.tfg.GoAway.modules.user.domain.user.User;

@Component
public class UserRegisterPostMapper {

    public User toDomain(UserRegisterPostRequest request) {
        return User.builder()
                .email(request.getEmail())
                .name(request.getName())
                .password(request.getPassword()) 
                .contactNumber(request.getContactNumber())
                .direction(null) 
                .userType(null)  
                .build();
    }

}
