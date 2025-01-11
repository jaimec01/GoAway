package com.tfg.GoAway.modules.user.infraestructure.in.http.register;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;



@Data
@NoArgsConstructor(force = true)
@AllArgsConstructor
public class UserRegisterPostRequest implements Serializable{


    private String email;

    private String name;

    private String password;

    private Long contactNumber;

}
