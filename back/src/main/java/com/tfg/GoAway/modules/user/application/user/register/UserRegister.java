package com.tfg.GoAway.modules.user.application.user.register;

import org.springframework.stereotype.Service;

import com.tfg.GoAway.modules.user.domain.User;
import com.tfg.GoAway.modules.user.domain.UserCriteria;
import com.tfg.GoAway.modules.user.domain.UserRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserRegister {

    private final UserRepository userRepository;
    private final UserRegisterMapper userRegisterMapper;

    public UserRegisterResponse register(UserRegisterRecord record) throws Exception {

        if(record.getEmail() != null){

            var user = userRepository.finder(UserCriteria.builder().email(record.getEmail()).build());

            if (!user.isPresent()){

                try {

                    User saved = userRepository.save(userRegisterMapper.toDomain(record, user.get()));
                    return UserRegisterResponse.builder().email(saved.getEmail()).build();

                } catch (Exception ex) {
                    log.error("[USER SAVED] Ha ocurrido un problema");
                    throw ex;
                }

            } else {
                log.error("[USER SAVED]");
                throw new Exception("El usuario ya existe");
            }
        } else {
            log.error("[USER SAVED]");
            throw new Exception("Usuario no encontrado");
        }

       
    }


}
