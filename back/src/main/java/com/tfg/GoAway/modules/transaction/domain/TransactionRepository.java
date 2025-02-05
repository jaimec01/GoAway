package com.tfg.GoAway.modules.transaction.domain;

import java.util.Optional;

public interface TransactionRepository {

    Transaction save(Transaction transaction);

    Optional<Transaction> findById(String transactionId);

}
