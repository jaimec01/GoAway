package com.tfg.GoAway.modules.user.infraestructure.in.http.register;

import java.io.Serializable;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserRegisterPostResponse implements Serializable {

    private final String email;
    
    private final String name;

    private final String message;

}
