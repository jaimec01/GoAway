package com.tfg.GoAway.modules.user.infrastructure.out.db.sql_server;

import jakarta.persistence.*;
import lombok.*;

@Data
@Table(name = "user")
@Entity
public class UserEntity {

    @Id
    private String email;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "contact_number")
    private Long contactNumber;

    @Column(name = "direction_id")
    private Integer  directionId;

    @Column(name = "user_type")
    private String userType;

    @Column(name = "user_rol")
    private String rol;

}
