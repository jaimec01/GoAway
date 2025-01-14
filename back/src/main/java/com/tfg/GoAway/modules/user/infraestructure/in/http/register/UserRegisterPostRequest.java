package com.tfg.GoAway.modules.user.infraestructure.in.http.register;

import java.io.Serializable;

import io.micrometer.common.lang.Nullable;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;



@Data
@NoArgsConstructor(force = true)
@AllArgsConstructor
public class UserRegisterPostRequest implements Serializable{

    @NotNull
    private String email;

    @NotNull
    private String name;

    @NotNull
    private String password;

    @Nullable
    private Long contactNumber;

}
