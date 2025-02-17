package com.tfg.GoAway.modules.advertisement.infrastructure.out.db.sql_server;

import com.tfg.GoAway.modules.advertisement.domain.Advertisement;
import com.tfg.GoAway.modules.advertisement.domain.AdvertisementRepository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.tfg.GoAway.modules.advertisement.infrastructure.out.db.sql_server.AdvertisementRepositoryMapper.*;

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
            AdvertisementEntity saved = repository.save(advertisementToEntity(advertisement));
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
    public List<Advertisement> findAllExcludingUser(String userEmail) {
        TypedQuery<AdvertisementEntity> query = CustomAdvertisementRepositoryQueryBuilder
                .buildQueryExcludingUser(userEmail, entityManager);
        List<AdvertisementEntity> advertisementEntities = query.getResultList();

        return advertisementEntities.stream()
                .map(AdvertisementRepositoryMapper::entityToAdvertisement)
                .collect(Collectors.toList());
    }

}
