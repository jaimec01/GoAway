package com.tfg.GoAway.user.user.infrastructure.out.db.sql_server;

import org.springframework.data.jpa.repository.JpaRepository;

public interface SqlServerJpaUserRepository extends JpaRepository<UserEntity, String> {

}
