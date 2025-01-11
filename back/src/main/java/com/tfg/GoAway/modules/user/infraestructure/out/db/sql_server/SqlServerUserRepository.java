package com.tfg.GoAway.modules.user.infraestructure.out.db.sql_server;

import org.springframework.stereotype.Repository;

import com.tfg.GoAway.modules.user.domain.user.User;
import com.tfg.GoAway.modules.user.domain.user.UserCriteria;
import com.tfg.GoAway.modules.user.domain.user.UserRepository;
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

    public SqlServerUserRepository(EntityManager entityManager, SqlServerJpaUserRepository repository){
        this.entityManager = entityManager;
        this.repository = repository;
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
            UserEntity saved = repository.save(userToEntity(user));
            return entityToUser(saved);
        } catch (Exception e) {
            log.error("Error al guardar el usuario: {}", e.getMessage(), e);
            throw e;
        }
    }

    @Override
    public Optional<User> findByEmail(String email) {

        if (email == null || email.isEmpty()) {
            throw new IllegalArgumentException("El email no puede ser nulo o vacío.");
        }

        return repository.findById(email).map(UserRepositoryMapper::entityToUser);
    }
    
}