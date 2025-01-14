package com.tfg.GoAway.modules.user.domain;

import java.util.Optional;

public interface UserRepository {

    Optional<User> finder(UserCriteria criteria);

    User save(User user);

    Optional<User> findByEmail(String email);

}
