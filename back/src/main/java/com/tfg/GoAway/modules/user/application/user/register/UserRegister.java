package com.tfg.GoAway.modules.user.application.user.register;

import org.springframework.stereotype.Service;

import com.tfg.GoAway.modules.user.domain.user.User;
import com.tfg.GoAway.modules.user.domain.user.UserRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserRegister {

    private final UserRepository userRepository;
    private final UserRegisterMapper userRegisterMapper;



    public UserRegisterResponse register(UserRegisterRecord record) {
        if (userRepository.findByEmail(record.getEmail()).isPresent()) {
            throw new IllegalArgumentException("El usuario con el email " + record.getEmail() + " ya existe.");
        }

        User user = userRegisterMapper.toDomain(record);
        User savedUser = userRepository.save(user);

        return userRegisterMapper.toResponse(savedUser, "Usuario registrado con éxito");
    }


}
