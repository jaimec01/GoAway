package com.tfg.GoAway.modules.user.domain;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Builder
@Data
@Getter
@Setter
public class User {

    private final String email;

    private final String name;

    private final String password;

    private final Long contactNumber;

    private final Integer direction;

    private final String userType;

    private final String rol;

}
