package com.tfg.GoAway.modules.advertisement.infrastructure.out.db.sql_server;

import com.tfg.GoAway.modules.advertisement.domain.AdvertisementCriteria;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import jakarta.persistence.Tuple;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public final class CustomAdvertisementRepositoryQueryBuilder {

    private static final String Q_BASE = "SELECT "
        + " id, "
        + " description, "
        + " furniture_category, "
        + " photo_urls, "
        + " furniture_condition, "
        + " user_email, "
        + " price"
        + " FROM advertisement "
        + " WHERE 1 = 1";

    private static final String Q_FILTER_CATEGORY = " AND furniture_category = :category ";
    private static final String Q_FILTER_CONDITION = " AND furniture_condition = :condition ";
    private static final String Q_FILTER_USER = " AND user_email = :userEmail ";

    private static final String PARAM_CATEGORY = "category";
    private static final String PARAM_CONDITION = "condition";
    private static final String PARAM_USER_EMAIL = "userEmail";

    public static Query buildQuery(final AdvertisementCriteria criteria, final EntityManager entityManager) {
        Query query = getQuery(criteria, entityManager);
        setParams(query, criteria);
        return query;
    }

    private static Query getQuery(final AdvertisementCriteria criteria, final EntityManager entityManager) {
        return entityManager.createNativeQuery(createQuery(criteria), Tuple.class);
    }

    private static String createQuery(final AdvertisementCriteria criteria) {
        final var query = new StringBuilder(Q_BASE);

        if (criteria.getFurnitureCategory() != null) {
            query.append(Q_FILTER_CATEGORY);
        }

        if (criteria.getFurnitureCondition() != null) {
            query.append(Q_FILTER_CONDITION);
        }

        if (criteria.getUserEmail() != null) {
            query.append(Q_FILTER_USER);
        }

        return query.toString();
    }

    private static void setParams(final Query query, final AdvertisementCriteria criteria) {
        if (criteria.getFurnitureCategory() != null) {
            query.setParameter(PARAM_CATEGORY, criteria.getFurnitureCategory());
        }

        if (criteria.getFurnitureCondition() != null) {
            query.setParameter(PARAM_CONDITION, criteria.getFurnitureCondition());
        }

        if (criteria.getUserEmail() != null) {
            query.setParameter(PARAM_USER_EMAIL, criteria.getUserEmail());
        }
    }
}
