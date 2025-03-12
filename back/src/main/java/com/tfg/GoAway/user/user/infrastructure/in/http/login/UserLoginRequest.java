package com.tfg.GoAway.user.user.infrastructure.in.http.login;


import java.io.Serializable;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor(force = true)
@AllArgsConstructor
public class UserLoginRequest implements Serializable{

    @NotNull
    private String email;

    @NotNull
    private String password;


}
