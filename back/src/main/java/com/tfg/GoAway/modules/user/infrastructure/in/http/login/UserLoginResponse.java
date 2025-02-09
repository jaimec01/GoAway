package com.tfg.GoAway.modules.user.infrastructure.in.http.login;

import java.io.Serializable;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserLoginResponse implements Serializable {

    private final String email;
    
    private final String name;

    private final String token;

}
