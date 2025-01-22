package com.tfg.GoAway.modules.advertisement.infrastructure.out.db.sql_server;

import com.tfg.GoAway.modules.advertisement.domain.Advertisement;
import com.tfg.GoAway.modules.advertisement.domain.AdvertisementRepository;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

import static com.tfg.GoAway.modules.advertisement.infrastructure.out.db.sql_server.AdvertisementRepositoryMapper.*;

@Slf4j
@Repository
public class SqlServerAdvertisementRepository implements AdvertisementRepository {

    private final SqlServerJpaAdvertisementRepository repository;

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
}
