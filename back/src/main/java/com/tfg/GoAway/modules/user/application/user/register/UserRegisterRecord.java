package com.tfg.GoAway.modules.user.application.user.register;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserRegisterRecord {
    
    private String email;

    private String name;

    private String password;

    private Long contactNumber;

    private Integer direction;
    
    private String userType;

}
