package com.tfg.GoAway.user.transaction.infrastructure.out.db.sql_server;

import lombok.extern.slf4j.Slf4j;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;

@Slf4j
public final class CustomTransactionRepositoryQueryBuilder {

    private static final String Q_BASE = "SELECT t FROM TransactionEntity t WHERE 1 = 1";
    private static final String Q_FILTER_BY_OWNER = " AND t.ownerEmail = :ownerEmail";
    private static final String Q_FILTER_BY_TENANT = " AND t.tenantEmail = :tenantEmail";
    private static final String Q_ORDER_BY_UPDATED_AT_DESC = " ORDER BY t.updatedAt DESC";
    private static final String Q_ORDER_BY_UPDATED_AT_ASC = " ORDER BY t.updatedAt ASC";

    private static final String PARAM_OWNER_EMAIL = "ownerEmail";
    private static final String PARAM_TENANT_EMAIL = "tenantEmail";

    private CustomTransactionRepositoryQueryBuilder() {
        throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
    }

    // Consultas para Owner
    public static TypedQuery<TransactionEntity> buildQueryForOwnerOrderByUpdatedAtDesc(
            final String ownerEmail, final EntityManager entityManager) {
        String queryString = Q_BASE + Q_FILTER_BY_OWNER + Q_ORDER_BY_UPDATED_AT_DESC;
        TypedQuery<TransactionEntity> query = entityManager.createQuery(queryString, TransactionEntity.class);
        query.setParameter(PARAM_OWNER_EMAIL, ownerEmail);
        return query;
    }

    public static TypedQuery<TransactionEntity> buildQueryForOwnerOrderByUpdatedAtAsc(
            final String ownerEmail, final EntityManager entityManager) {
        String queryString = Q_BASE + Q_FILTER_BY_OWNER + Q_ORDER_BY_UPDATED_AT_ASC;
        TypedQuery<TransactionEntity> query = entityManager.createQuery(queryString, TransactionEntity.class);
        query.setParameter(PARAM_OWNER_EMAIL, ownerEmail);
        return query;
    }

    // Consultas para Tenant
    public static TypedQuery<TransactionEntity> buildQueryForTenantOrderByUpdatedAtDesc(
            final String tenantEmail, final EntityManager entityManager) {
        String queryString = Q_BASE + Q_FILTER_BY_TENANT + Q_ORDER_BY_UPDATED_AT_DESC;
        TypedQuery<TransactionEntity> query = entityManager.createQuery(queryString, TransactionEntity.class);
        query.setParameter(PARAM_TENANT_EMAIL, tenantEmail);
        return query;
    }

    public static TypedQuery<TransactionEntity> buildQueryForTenantOrderByUpdatedAtAsc(
            final String tenantEmail, final EntityManager entityManager) {
        String queryString = Q_BASE + Q_FILTER_BY_TENANT + Q_ORDER_BY_UPDATED_AT_ASC;
        TypedQuery<TransactionEntity> query = entityManager.createQuery(queryString, TransactionEntity.class);
        query.setParameter(PARAM_TENANT_EMAIL, tenantEmail);
        return query;
    }
}