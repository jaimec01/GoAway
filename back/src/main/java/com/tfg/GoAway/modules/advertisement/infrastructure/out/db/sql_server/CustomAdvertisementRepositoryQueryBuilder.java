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

    private static final String PARAM_CATEGORY = "category";
    private static final String PARAM_CONDITION = "condition";

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
}
