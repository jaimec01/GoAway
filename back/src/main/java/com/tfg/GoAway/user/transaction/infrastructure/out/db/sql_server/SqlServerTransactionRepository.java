package com.tfg.GoAway.user.transaction.infrastructure.out.db.sql_server;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import com.tfg.GoAway.user.advertisement.infrastructure.out.db.sql_server.AdvertisementEntity;
import com.tfg.GoAway.user.advertisement.infrastructure.out.db.sql_server.SqlServerJpaAdvertisementRepository;
import com.tfg.GoAway.user.transaction.domain.Transaction;
import com.tfg.GoAway.user.transaction.domain.TransactionRepository;

import static com.tfg.GoAway.user.transaction.infrastructure.out.db.sql_server.TransactionRepositoryMapper.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Repository
public class SqlServerTransactionRepository implements TransactionRepository {

    private final SqlServerJpaTransactionRepository repository;
    private final SqlServerJpaAdvertisementRepository advertisementRepository;

    @PersistenceContext
    private EntityManager entityManager;

    public SqlServerTransactionRepository(SqlServerJpaTransactionRepository repository, SqlServerJpaAdvertisementRepository advertisementRepository) {
        this.repository = repository;
        this.advertisementRepository = advertisementRepository;
    }

    @Override
    public Transaction save(final Transaction transaction) {
        try {
            AdvertisementEntity advertisement = advertisementRepository.findById(transaction.getAdvertisementId())
                    .orElseThrow(() -> new IllegalArgumentException("El anuncio asociado a esta transacción no existe"));

            TransactionEntity transactionEntity = transactionToEntity(transaction, advertisement);

            TransactionEntity saved = repository.save(transactionEntity);

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
        return findByOwnerEmailOrderByUpdatedAtDesc(ownerEmail); // Por defecto descendente
    }

    public List<Transaction> findByOwnerEmailOrderByUpdatedAtDesc(String ownerEmail) {
        TypedQuery<TransactionEntity> query = CustomTransactionRepositoryQueryBuilder
                .buildQueryForOwnerOrderByUpdatedAtDesc(ownerEmail, entityManager);
        List<TransactionEntity> transactionEntities = query.getResultList();
        return transactionEntities.stream()
                .map(TransactionRepositoryMapper::entityToTransaction)
                .collect(Collectors.toList());
    }

    public List<Transaction> findByOwnerEmailOrderByUpdatedAtAsc(String ownerEmail) {
        TypedQuery<TransactionEntity> query = CustomTransactionRepositoryQueryBuilder
                .buildQueryForOwnerOrderByUpdatedAtAsc(ownerEmail, entityManager);
        List<TransactionEntity> transactionEntities = query.getResultList();
        return transactionEntities.stream()
                .map(TransactionRepositoryMapper::entityToTransaction)
                .collect(Collectors.toList());
    }

    @Override
    public List<Transaction> findByTenantEmail(String tenantEmail) {
        return findByTenantEmailOrderByUpdatedAtDesc(tenantEmail); // Por defecto descendente
    }

    public List<Transaction> findByTenantEmailOrderByUpdatedAtDesc(String tenantEmail) {
        TypedQuery<TransactionEntity> query = CustomTransactionRepositoryQueryBuilder
                .buildQueryForTenantOrderByUpdatedAtDesc(tenantEmail, entityManager);
        List<TransactionEntity> transactionEntities = query.getResultList();
        return transactionEntities.stream()
                .map(TransactionRepositoryMapper::entityToTransaction)
                .collect(Collectors.toList());
    }

    public List<Transaction> findByTenantEmailOrderByUpdatedAtAsc(String tenantEmail) {
        TypedQuery<TransactionEntity> query = CustomTransactionRepositoryQueryBuilder
                .buildQueryForTenantOrderByUpdatedAtAsc(tenantEmail, entityManager);
        List<TransactionEntity> transactionEntities = query.getResultList();
        return transactionEntities.stream()
                .map(TransactionRepositoryMapper::entityToTransaction)
                .collect(Collectors.toList());
    }
}