package com.tfg.GoAway.modules.user.infraestructure.in.http.register;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tfg.GoAway.modules.user.domain.User;
import com.tfg.GoAway.modules.user.infraestructure.out.db.sql_server.SqlServerUserRepository;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class UserRegisterPostController {

    private final SqlServerUserRepository userRepository;
    private final UserRegisterPostMapper requestMapper;

    @PostMapping("/register")
    public UserRegisterPostResponse saveUser(@RequestBody UserRegisterPostRequest request) {

        if (request.getEmail() == null || request.getEmail().isEmpty()) {
            throw new IllegalArgumentException("El email no puede ser nulo o vacío.");
        }


        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new IllegalArgumentException("El email ya está en uso.");
        }


        User user = requestMapper.toDomain(request);
        User savedUser = userRepository.save(user);


        return new UserRegisterPostResponse(
            savedUser.getEmail(),
            savedUser.getName(),
            "Usuario registrado con éxito"
        );
    }
}
