package com.tfg.GoAway.modules.transaction.infrastructure.out.db.sql_server;

import com.tfg.GoAway.modules.transaction.domain.Transaction;
import com.tfg.GoAway.modules.transaction.domain.TransactionRepository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import static com.tfg.GoAway.modules.transaction.infrastructure.out.db.sql_server.TransactionRepositoryMapper.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Slf4j
@Repository
public class SqlServerTransactionRepository implements TransactionRepository {

    private final SqlServerJpaTransactionRepository repository;

    @PersistenceContext
    private EntityManager entityManager;

    public SqlServerTransactionRepository(SqlServerJpaTransactionRepository repository) {
        this.repository = repository;
    }

    @Override
    public Transaction save(final Transaction transaction) {
        try {
            TransactionEntity saved = repository.save(transactionToEntity(transaction));
            return entityToTransaction(saved);
        } catch (Exception e) {
            log.error("Error al guardar la transacción: {}", e.getMessage(), e);
            throw e;
        }
    }

    @Override
    public Optional<Transaction> findById(String transactionId) {
        return repository.findById(transactionId).map(TransactionRepositoryMapper::entityToTransaction);
    }

    
    @Override
    public List<Transaction> findByOwnerEmail(String ownerEmail) {
        TypedQuery<TransactionEntity> query = CustomTransactionRepositoryQueryBuilder.buildQueryForOwner(ownerEmail, entityManager);
        List<TransactionEntity> transactionEntities = query.getResultList();

        return transactionEntities.stream()
                .map(TransactionRepositoryMapper::entityToTransaction)
                .collect(Collectors.toList());
    }

    @Override
    public List<Transaction> findByTenantEmail(String tenantEmail) {
        TypedQuery<TransactionEntity> query = CustomTransactionRepositoryQueryBuilder.buildQueryForTenant(tenantEmail, entityManager);
        List<TransactionEntity> transactionEntities = query.getResultList();

        return transactionEntities.stream()
                .map(TransactionRepositoryMapper::entityToTransaction)
                .collect(Collectors.toList());
    }



}