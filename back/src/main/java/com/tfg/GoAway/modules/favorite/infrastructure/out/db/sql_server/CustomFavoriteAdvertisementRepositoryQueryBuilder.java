package com.tfg.GoAway.modules.favorite.infrastructure.out.db.sql_server;

import com.tfg.GoAway.modules.advertisement.infrastructure.out.db.sql_server.AdvertisementEntity;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;

public final class CustomFavoriteAdvertisementRepositoryQueryBuilder {

    private static final String Q_BASE = "SELECT a FROM AdvertisementEntity a WHERE a.id IN " +
            "(SELECT f.id.advertisementId FROM FavoriteAdvertisementEntity f WHERE f.id.userEmail = :userEmail)";

    private static final String PARAM_USER_EMAIL = "userEmail";

    private CustomFavoriteAdvertisementRepositoryQueryBuilder() {
        throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
    }

    public static TypedQuery<AdvertisementEntity> buildQueryByUserEmail(final String userEmail, final EntityManager entityManager) {
        TypedQuery<AdvertisementEntity> query = entityManager.createQuery(Q_BASE, AdvertisementEntity.class);
        query.setParameter(PARAM_USER_EMAIL, userEmail);
        return query;
    }
}
