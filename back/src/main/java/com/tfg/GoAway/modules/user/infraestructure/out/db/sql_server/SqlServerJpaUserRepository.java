package com.tfg.GoAway.modules.user.infraestructure.out.db.sql_server;

import org.springframework.data.jpa.repository.JpaRepository;

public interface SqlServerJpaUserRepository extends JpaRepository<UserEntity, String> {

}
