package com.tfg.GoAway.modules.advertisement.infrastructure.out.db.sql_server;

import lombok.extern.slf4j.Slf4j;
import com.tfg.GoAway.modules.advertisement.domain.AdvertisementCategory;
import com.tfg.GoAway.modules.advertisement.domain.AdvertisementCondition;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;

@Slf4j
public final class CustomAdvertisementRepositoryQueryBuilder {

    private static final String Q_BASE = "SELECT a FROM AdvertisementEntity a WHERE 1 = 1";
    private static final String Q_FILTER_CATEGORY = " AND a.furnitureCategory = :category";
    private static final String Q_FILTER_CONDITION = " AND a.furnitureCondition = :condition";
    private static final String Q_FILTER_ID = " AND a.id = :id";
    private static final String Q_FILTER_USER_EMAIL = " AND a.userEmail = :userEmail";

    private static final String PARAM_CATEGORY = "category";
    private static final String PARAM_CONDITION = "condition";
    private static final String PARAM_ID = "id";
    private static final String PARAM_USER_EMAIL = "userEmail";

    private CustomAdvertisementRepositoryQueryBuilder() {
        throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
    }

    public static TypedQuery<AdvertisementEntity> buildQueryByFilters(final String category, final String condition, final EntityManager entityManager) {

        StringBuilder queryString = new StringBuilder(Q_BASE);

        if (category != null) {
            queryString.append(Q_FILTER_CATEGORY);
        }
        if (condition != null) {
            queryString.append(Q_FILTER_CONDITION);
        }

        TypedQuery<AdvertisementEntity> query = entityManager.createQuery(queryString.toString(), AdvertisementEntity.class);

        if (category != null) {
            try {
                query.setParameter(PARAM_CATEGORY, AdvertisementCategory.valueOf(category.toUpperCase()));
            } catch (IllegalArgumentException e) {
                throw new IllegalArgumentException("Categoría inválida: " + category);
            }
        }

        if (condition != null) {
            try {
                query.setParameter(PARAM_CONDITION, AdvertisementCondition.valueOf(condition.toUpperCase()));
            } catch (IllegalArgumentException e) {
                throw new IllegalArgumentException("Condición inválida: " + condition);
            }
        }

        return query;
    }

    public static TypedQuery<AdvertisementEntity> buildQueryByIdAndUserEmail(final String id, final String userEmail, final EntityManager entityManager) {
        StringBuilder queryString = new StringBuilder(Q_BASE);

        if (id != null) {
            queryString.append(Q_FILTER_ID);
        }
        if (userEmail != null) {
            queryString.append(Q_FILTER_USER_EMAIL);
        }

        TypedQuery<AdvertisementEntity> query = entityManager.createQuery(queryString.toString(), AdvertisementEntity.class);

        if (id != null) {
            query.setParameter(PARAM_ID, id);
        }
        if (userEmail != null) {
            query.setParameter(PARAM_USER_EMAIL, userEmail);
        }

        return query;
    }

    public static TypedQuery<AdvertisementEntity> buildQueryByFiltersAndExcludeUser(
        final String userEmail, final String category, final String condition, final EntityManager entityManager) {

    StringBuilder queryString = new StringBuilder(Q_BASE);

    if (userEmail != null) {
        queryString.append(" AND a.userEmail <> :userEmail");
    }
    if (category != null) {
        queryString.append(Q_FILTER_CATEGORY);
    }
    if (condition != null) {
        queryString.append(Q_FILTER_CONDITION);
    }

    TypedQuery<AdvertisementEntity> query = entityManager.createQuery(queryString.toString(), AdvertisementEntity.class);

    if (userEmail != null) {
        query.setParameter(PARAM_USER_EMAIL, userEmail);
    }
    if (category != null) {
        try {
            query.setParameter(PARAM_CATEGORY, AdvertisementCategory.valueOf(category.toUpperCase()));
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Categoría inválida: " + category);
        }
    }
    if (condition != null) {
        try {
            query.setParameter(PARAM_CONDITION, AdvertisementCondition.valueOf(condition.toUpperCase()));
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Condición inválida: " + condition);
        }
    }

    return query;
}


   
}
