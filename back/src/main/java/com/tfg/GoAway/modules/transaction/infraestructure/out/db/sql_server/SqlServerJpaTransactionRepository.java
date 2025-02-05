package com.tfg.GoAway.modules.transaction.infraestructure.out.db.sql_server;

import org.springframework.data.jpa.repository.JpaRepository;

public interface SqlServerJpaTransactionRepository extends JpaRepository<TransactionEntity, String> {
}