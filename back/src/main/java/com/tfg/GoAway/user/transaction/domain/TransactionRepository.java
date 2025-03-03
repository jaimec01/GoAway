package com.tfg.GoAway.user.transaction.domain;

import java.util.List;
import java.util.Optional;

public interface TransactionRepository {

    Transaction save(Transaction transaction);

    Optional<Transaction> findById(String transactionId);

    List<Transaction> findByOwnerEmail(String ownerEmail);
    
    List<Transaction> findByTenantEmail(String tenantEmail);

}
