package com.tfg.GoAway.modules.user.infraestructure.in.http.login;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
public class UserLoginController {

    @PostMapping("/prueba")
    public String testEndpoint() {
        System.out.println("Llegó al controlador básico.");
        return "Controlador funcionando correctamente.";
    }
}
