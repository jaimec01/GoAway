package com.tfg.GoAway.user.user.infrastructure.in.http.register;

import java.io.Serializable;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserRegisterPostResponse implements Serializable {

    private final String email;
    
    private final String name;

    private String token;

    private final String message;



}
