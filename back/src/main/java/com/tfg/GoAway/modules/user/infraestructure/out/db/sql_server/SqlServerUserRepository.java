package com.tfg.GoAway.modules.user.infraestructure.out.db.sql_server;

import org.springframework.stereotype.Repository;

import com.tfg.GoAway.modules.shared.password.PasswordEncoderService;
import com.tfg.GoAway.modules.user.domain.User;
import com.tfg.GoAway.modules.user.domain.UserCriteria;
import com.tfg.GoAway.modules.user.domain.UserRepository;

import jakarta.persistence.*;
import lombok.extern.slf4j.Slf4j;
import java.util.Optional;
import java.util.List;

import static com.tfg.GoAway.modules.user.infraestructure.out.db.sql_server.UserRepositoryMapper.*;

@Slf4j
@Repository
public class SqlServerUserRepository implements UserRepository{

    private final EntityManager entityManager;

    private final SqlServerJpaUserRepository repository;

    private final PasswordEncoderService passwordEncoderService;


    public SqlServerUserRepository(EntityManager entityManager, SqlServerJpaUserRepository repository, PasswordEncoderService passwordEncoderService){
        this.entityManager = entityManager;
        this.repository = repository;
        this.passwordEncoderService = passwordEncoderService;
    }

    @SuppressWarnings("unchecked")
    @Override
    public Optional<User> finder(UserCriteria criteria) {
        final List<Tuple> users = CustomUserRepositoryQueryBuilder
                .buildQuery(criteria, entityManager).getResultList();
    
        return tupleToUser(users);
    }

    @Override
    public User save(final User user) {
        try {
            UserEntity saved = repository.save(
                UserRepositoryMapper.userToEntity(user, passwordEncoderService) 
            );
            return UserRepositoryMapper.entityToUser(saved);
        } catch (Exception e) {
            log.error("Error al guardar el usuario: {}", e.getMessage(), e);
            throw e;
        }
    }

    @Override
    public Optional<User> findByEmail(String email) {
    
        if (email == null || email.isEmpty()) {
            return Optional.empty();
        }
    
        return repository.findById(email).map(UserRepositoryMapper::entityToUser);
    }
    
    
}