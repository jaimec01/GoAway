package com.tfg.GoAway.modules.transaction.infraestructure.out.db.sql_server;

import com.tfg.GoAway.modules.transaction.domain.Transaction;
import com.tfg.GoAway.modules.transaction.domain.TransactionOwnerConfirmation;
import com.tfg.GoAway.modules.transaction.domain.TransactionRepository;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import static com.tfg.GoAway.modules.transaction.infraestructure.out.db.sql_server.TransactionRepositoryMapper.*;

import java.util.Optional;


@Slf4j
@Repository
public class SqlServerTransactionRepository implements TransactionRepository {

    private final SqlServerJpaTransactionRepository repository;

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

    public Optional<Transaction> acceptTransaction(String transactionId) {
        return updateTransactionStatus(transactionId, TransactionOwnerConfirmation.ACCEPTED);
    }

    public Optional<Transaction> rejectTransaction(String transactionId) {
        return updateTransactionStatus(transactionId, TransactionOwnerConfirmation.REJECTED);
    }

    private Optional<Transaction> updateTransactionStatus(String transactionId, TransactionOwnerConfirmation newStatus) {
        Optional<TransactionEntity> optionalTransaction = repository.findById(transactionId);
        if (optionalTransaction.isPresent()) {
            TransactionEntity transaction = optionalTransaction.get();
            transaction.setOwnerConfirmation(newStatus);
            repository.save(transaction);
            return Optional.of(entityToTransaction(transaction));
        }
        return Optional.empty();
    }



}