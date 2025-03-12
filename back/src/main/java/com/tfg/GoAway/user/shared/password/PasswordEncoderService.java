package com.tfg.GoAway.user.shared.password;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class PasswordEncoderService {
    private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    // Hashea la contraseña
    public String encode(String rawPassword) {
        return passwordEncoder.encode(rawPassword);
    }

    // Verifica la contraseña con el hash almacenado
    public boolean matches(String rawPassword, String encodedPassword) {
        return passwordEncoder.matches(rawPassword, encodedPassword);
    }
}
