package com.tfg.GoAway.user.user.domain;

import lombok.*;


@Builder
@Getter
public class UserCriteria {

    private final String email;

    private final String name;

    private final Integer direction;


}
