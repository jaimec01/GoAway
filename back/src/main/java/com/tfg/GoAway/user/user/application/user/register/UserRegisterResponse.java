package com.tfg.GoAway.user.user.application.user.register;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class UserRegisterResponse {

    private final String email;

    private final String name;

    private final Long contactNumber;

    private final String message;
}
 