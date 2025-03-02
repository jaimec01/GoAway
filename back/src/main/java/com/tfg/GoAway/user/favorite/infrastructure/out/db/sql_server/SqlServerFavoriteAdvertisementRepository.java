package com.tfg.GoAway.user.favorite.infrastructure.out.db.sql_server;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import lombok.extern.slf4j.Slf4j;

import static com.tfg.GoAway.user.favorite.infrastructure.out.db.sql_server.FavoriteAdvertisementRepositoryMapper.*;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Repository;

import com.tfg.GoAway.user.advertisement.domain.Advertisement;
import com.tfg.GoAway.user.advertisement.infrastructure.out.db.sql_server.AdvertisementEntity;
import com.tfg.GoAway.user.advertisement.infrastructure.out.db.sql_server.AdvertisementRepositoryMapper;
import com.tfg.GoAway.user.favorite.domain.FavoriteAdvertisement;
import com.tfg.GoAway.user.favorite.domain.FavoriteAdvertisementRepository;
import com.tfg.GoAway.user.shared.security.SecurityUtils;

@Slf4j
@Repository
public class SqlServerFavoriteAdvertisementRepository implements FavoriteAdvertisementRepository {

    @PersistenceContext
    private EntityManager entityManager;

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

    @Override
    public List<Advertisement> findFavoritesByUserEmail(String userEmail) {
        TypedQuery<AdvertisementEntity> query = CustomFavoriteAdvertisementRepositoryQueryBuilder.buildQueryByUserEmail(userEmail, entityManager);
        List<AdvertisementEntity> advertisementEntities = query.getResultList();
        
        return advertisementEntities.stream()
                .map(AdvertisementRepositoryMapper::entityToAdvertisement)
                .collect(Collectors.toList());
    }

}
