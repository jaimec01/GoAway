package com.tfg.GoAway.user.user.infrastructure.in.http.login;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tfg.GoAway.shared.exception.CustomException;
import com.tfg.GoAway.shared.password.PasswordEncoderService;
import com.tfg.GoAway.shared.security.JwtTokenProvider;
import com.tfg.GoAway.user.user.domain.User;
import com.tfg.GoAway.user.user.domain.UserRepository;

import lombok.RequiredArgsConstructor;

import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth/user")
public class UserLoginController {

    private final UserRepository userRepository;
    private final PasswordEncoderService passwordEncoderService;
    private final JwtTokenProvider jwtTokenProvider;

    @PostMapping("/login")
    public ResponseEntity<Object> login(@RequestBody UserLoginRequest request) {
        if (request.getEmail() == null || request.getEmail().isEmpty()) {
            throw new CustomException("El email no puede ser nulo o vacío.");
        }

        if (request.getPassword() == null || request.getPassword().isEmpty()) {
            throw new CustomException("La contraseña no puede ser nula o vacía.");
        }

        Optional<User> optionalUser = userRepository.findByEmail(request.getEmail());
        if (optionalUser.isEmpty()) {
            throw new CustomException("Usuario no encontrado.");
        }

        User user = optionalUser.get();

        if (!passwordEncoderService.matches(request.getPassword(), user.getPassword())) {
            throw new CustomException("Contraseña incorrecta.");
        }

        String token = jwtTokenProvider.generateToken(user.getEmail(), user.getName(), user.getRol());

        return ResponseEntity.ok(new UserLoginResponse(
            user.getEmail(),
            user.getName(),
            token
        ));
    }
}
