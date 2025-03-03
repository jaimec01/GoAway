package com.tfg.GoAway.user.transaction.infrastructure.out.db.sql_server;

import lombok.extern.slf4j.Slf4j;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;

@Slf4j
public final class CustomTransactionRepositoryQueryBuilder {

    private static final String Q_BASE = "SELECT t FROM TransactionEntity t WHERE 1 = 1";
    private static final String Q_FILTER_BY_OWNER = " AND t.ownerEmail = :ownerEmail";
    private static final String Q_FILTER_BY_TENANT = " AND t.tenantEmail = :tenantEmail";

    private static final String PARAM_OWNER_EMAIL = "ownerEmail";
    private static final String PARAM_TENANT_EMAIL = "tenantEmail";

    private CustomTransactionRepositoryQueryBuilder() {
        throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
    }

    public static TypedQuery<TransactionEntity> buildQueryForOwner(final String ownerEmail, final EntityManager entityManager) {
        return buildQuery(Q_FILTER_BY_OWNER, PARAM_OWNER_EMAIL, ownerEmail, entityManager);
    }

    public static TypedQuery<TransactionEntity> buildQueryForTenant(final String tenantEmail, final EntityManager entityManager) {
        return buildQuery(Q_FILTER_BY_TENANT, PARAM_TENANT_EMAIL, tenantEmail, entityManager);
    }

    private static TypedQuery<TransactionEntity> buildQuery(final String filter, final String param, final String email, final EntityManager entityManager) {
        String queryString = Q_BASE + filter;
        TypedQuery<TransactionEntity> query = entityManager.createQuery(queryString, TransactionEntity.class);
        query.setParameter(param, email);
        return query;
    }
}
