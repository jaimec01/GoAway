package com.tfg.GoAway.modules.user.domain.user;

import lombok.*;


@Builder
@Getter
public class UserCriteria {

    private final String email;

    private final String name;

    private final Integer direction;


}
