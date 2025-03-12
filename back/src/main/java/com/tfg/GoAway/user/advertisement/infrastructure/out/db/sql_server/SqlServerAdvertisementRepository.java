package com.tfg.GoAway.user.advertisement.infrastructure.out.db.sql_server;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import com.tfg.GoAway.user.advertisement.domain.Advertisement;
import com.tfg.GoAway.user.advertisement.domain.AdvertisementRepository;

import static com.tfg.GoAway.user.advertisement.infrastructure.out.db.sql_server.AdvertisementRepositoryMapper.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Repository
public class SqlServerAdvertisementRepository implements AdvertisementRepository {

    private final SqlServerJpaAdvertisementRepository repository;

    @PersistenceContext
    private EntityManager entityManager;

    public SqlServerAdvertisementRepository(SqlServerJpaAdvertisementRepository repository) {
        this.repository = repository;
    }

    @Override
    public Advertisement save(final Advertisement advertisement) {
        try {
            // 1) Convertimos del dominio (Advertisement) a entidad JPA
            // (AdvertisementEntity)
            AdvertisementEntity entity = advertisementToEntity(advertisement);

            // 2) Forzamos que siempre esté activo si no está definido
            if (entity.getActive() == null) {
                entity.setActive(true);
            }

            // 3) Guardamos con Spring Data JPA (se generan los IDs y se rellenan las FKs en
            // las fotos)
            AdvertisementEntity saved = repository.save(entity);

            // 4) Convertimos de vuelta la entidad guardada a dominio
            return entityToAdvertisement(saved);

        } catch (Exception e) {
            log.error("Error al guardar el anuncio: {}", e.getMessage(), e);
            throw e;
        }
    }

    @Override
    public Optional<Advertisement> findById(String id) {
        if (id == null) {
            return Optional.empty();
        }
        return repository.findById(id.toString()).map(AdvertisementRepositoryMapper::entityToAdvertisement);
    }

    @Override
    public List<Advertisement> findAll() {
        return repository.findAll().stream()
                .map(AdvertisementRepositoryMapper::entityToAdvertisement)
                .toList();
    }

    @Override
    public void delete(Advertisement advertisement) {
        try {
            repository.delete(advertisementToEntity(advertisement));
        } catch (Exception e) {
            log.error("Error al eliminar el anuncio: {}", e.getMessage(), e);
            throw e;
        }
    }

    @Override
    public List<Advertisement> findByFilters(String category, String condition) {
        TypedQuery<AdvertisementEntity> query = CustomAdvertisementRepositoryQueryBuilder.buildQueryByFilters(category,
                condition, entityManager);
        List<AdvertisementEntity> advertisementEntities = query.getResultList();

        return advertisementEntities.stream()
                .map(AdvertisementRepositoryMapper::entityToAdvertisement)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<Advertisement> findByIdAndUserEmail(String id, String userEmail) {
        if (id == null || userEmail == null) {
            return Optional.empty();
        }

        TypedQuery<AdvertisementEntity> query = CustomAdvertisementRepositoryQueryBuilder.buildQueryByIdAndUserEmail(id,
                userEmail, entityManager);

        List<AdvertisementEntity> results = query.getResultList();

        if (results.isEmpty()) {
            return Optional.empty();
        }

        return Optional.of(AdvertisementRepositoryMapper.entityToAdvertisement(results.get(0)));
    }

    @Override
    public List<Advertisement> findByFiltersAndExcludeUser(String userEmail, String category, String condition) {
        TypedQuery<AdvertisementEntity> query = CustomAdvertisementRepositoryQueryBuilder
                .buildQueryByFiltersAndExcludeUser(userEmail, category, condition, entityManager);

        List<AdvertisementEntity> advertisementEntities = query.getResultList();
        return advertisementEntities.stream()
                .map(AdvertisementRepositoryMapper::entityToAdvertisement)
                .collect(Collectors.toList());
    }

    @Override
    public List<Advertisement> findByFiltersOrderByUpdatedAtDesc(String category, String condition) {
        TypedQuery<AdvertisementEntity> query = CustomAdvertisementRepositoryQueryBuilder
                .buildQueryByFiltersOrderByUpdatedAtDesc(category, condition, entityManager);
        return query.getResultList().stream()
                .map(AdvertisementRepositoryMapper::entityToAdvertisement)
                .collect(Collectors.toList());
    }

    @Override
    public List<Advertisement> findByFiltersOrderByUpdatedAtAsc(String category, String condition) {
        TypedQuery<AdvertisementEntity> query = CustomAdvertisementRepositoryQueryBuilder
                .buildQueryByFiltersOrderByUpdatedAtAsc(category, condition, entityManager);
        return query.getResultList().stream()
                .map(AdvertisementRepositoryMapper::entityToAdvertisement)
                .collect(Collectors.toList());
    }

    @Override
    public List<Advertisement> findByFiltersAndExcludeUserOrderByUpdatedAtDesc(String userEmail, String category,
            String condition) {
        TypedQuery<AdvertisementEntity> query = CustomAdvertisementRepositoryQueryBuilder
                .buildQueryByFiltersAndExcludeUserOrderByUpdatedAtDesc(userEmail, category, condition, entityManager);
        return query.getResultList().stream()
                .map(AdvertisementRepositoryMapper::entityToAdvertisement)
                .collect(Collectors.toList());
    }

    @Override
    public List<Advertisement> findByFiltersAndExcludeUserOrderByUpdatedAtAsc(String userEmail, String category,
            String condition) {
        TypedQuery<AdvertisementEntity> query = CustomAdvertisementRepositoryQueryBuilder
                .buildQueryByFiltersAndExcludeUserOrderByUpdatedAtAsc(userEmail, category, condition, entityManager);
        return query.getResultList().stream()
                .map(AdvertisementRepositoryMapper::entityToAdvertisement)
                .collect(Collectors.toList());
    }

    @Override
    public List<Advertisement> findByUserEmailOrderByUpdatedAtDesc(String userEmail) {
        TypedQuery<AdvertisementEntity> query = CustomAdvertisementRepositoryQueryBuilder
                .buildQueryByUserEmailOrderByUpdatedAtDesc(userEmail, entityManager);

        return query.getResultList().stream()
                .map(AdvertisementRepositoryMapper::entityToAdvertisement)
                .collect(Collectors.toList());
    }
}