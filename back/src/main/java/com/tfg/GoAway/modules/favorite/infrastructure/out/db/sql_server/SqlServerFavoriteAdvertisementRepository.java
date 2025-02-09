package com.tfg.GoAway.modules.favorite.infrastructure.out.db.sql_server;

import com.tfg.GoAway.modules.favorite.domain.FavoriteAdvertisement;
import com.tfg.GoAway.modules.favorite.domain.FavoriteAdvertisementRepository;
import com.tfg.GoAway.modules.shared.security.SecurityUtils;

import lombok.extern.slf4j.Slf4j;

import static com.tfg.GoAway.modules.favorite.infrastructure.out.db.sql_server.FavoriteAdvertisementRepositoryMapper.*;

import org.springframework.stereotype.Repository;

@Slf4j
@Repository
public class SqlServerFavoriteAdvertisementRepository implements FavoriteAdvertisementRepository {

    private final SqlServerJpaFavoriteAdvertisementRepository repository;

    public SqlServerFavoriteAdvertisementRepository(SqlServerJpaFavoriteAdvertisementRepository repository) {
        this.repository = repository;
    }

    @Override
    public FavoriteAdvertisement save(final FavoriteAdvertisement favorite) {
        try {
            FavoriteAdvertisementEntity saved = repository.save(toEntity(favorite));
            return toDomain(saved);
        } catch (Exception e) {
            log.error("Error al guardar el favorito: {}", e.getMessage(), e);
            throw e;
        }
    }

    @Override
    public boolean existsById(String advertisementId) {
        try {
            String userEmail = SecurityUtils.getUserEmailFromContext();
            return repository.existsById(new FavoriteAdvertisementId(userEmail, advertisementId));
        } catch (Exception e) {
            log.error("Error al verificar la existencia del favorito: {}", e.getMessage(), e);
            throw e;
        }
    }

    @Override
    public void delete(String advertisementId) {
        try {
            String userEmail = SecurityUtils.getUserEmailFromContext();
            repository.deleteById(new FavoriteAdvertisementId(userEmail, advertisementId));
        } catch (Exception e) {
            log.error("Error al eliminar el favorito: {}", e.getMessage(), e);
            throw e;
        }
    }

}
