package com.tfg.GoAway.modules.user.infraestructure.out.db.sql_server;

import lombok.extern.slf4j.Slf4j;

import java.math.BigInteger;
import java.util.*;

import com.tfg.GoAway.modules.user.domain.user.User;

import jakarta.persistence.Tuple;

@Slf4j
public class UserRepositoryMapper {

    private UserRepositoryMapper() {
        throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
    }

    public static Optional<User> tupleToUser(final List<Tuple> tuples){
        var firstUser = tuples.stream().findFirst();
        if (firstUser.isPresent()) {
            var user = User.builder()
                    .email(firstUser.get().get("email", String.class))
                    .name(firstUser.get().get("name", String.class))
                    .password(firstUser.get().get("password", String.class))
                    .contactNumber(firstUser.get().get("contactNumber", BigInteger.class).longValue())
                    .direction(firstUser.get().get("direction", BigInteger.class) != null
                            ? firstUser.get().get("direction", BigInteger.class).intValue()
                            : null)
                    .userType(firstUser.get().get("userType", String.class))
                    .build();

            return Optional.of(user);
        } else {
            return Optional.empty();
        }
    }

    public static UserEntity userToEntity(final User input) {

        var output = new UserEntity();

        output.setEmail(input.getEmail());
        output.setName(input.getName());
        output.setPassword(input.getPassword());
        output.setContactNumber(input.getContactNumber());
        if (input.getDirection() != null) {
            output.setDirectionId(input.getDirection());
        } else {
            output.setDirectionId(null);
        }
        output.setUserType(input.getUserType());

        return output;
    }

    public static User entityToUser(final UserEntity input) {
        return User.builder()
                .email(input.getEmail())
                .name(input.getName())
                .password(input.getPassword())
                .contactNumber(input.getContactNumber())
                .direction(input.getDirectionId())
                .userType(input.getUserType())
                .build();
    }

}
