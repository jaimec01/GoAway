package com.tfg.GoAway.modules.transaction.infrastructure.out.db.sql_server;

import com.tfg.GoAway.modules.transaction.domain.Transaction;

public class TransactionRepositoryMapper {

    public static TransactionEntity transactionToEntity(Transaction transaction) {
        TransactionEntity entity = new TransactionEntity();
        entity.setId(transaction.getId());
        entity.setStartDate(transaction.getStartDate());
        entity.setEndDate(transaction.getEndDate());
        entity.setTotalPrice(transaction.getTotalPrice());
        entity.setPaymentMethod(transaction.getPaymentMethod());
        entity.setStatus(transaction.getStatus());
        entity.setTenantEmail(transaction.getTenantEmail());
        entity.setOwnerEmail(transaction.getOwnerEmail());
        entity.setAdvertisementId(transaction.getAdvertisementId());
        entity.setOwnerConfirmation(transaction.getOwnerConfirmation());
        return entity;
    }

    public static Transaction entityToTransaction(TransactionEntity entity) {
        return Transaction.builder()
                .id(entity.getId())
                .startDate(entity.getStartDate())
                .endDate(entity.getEndDate())
                .totalPrice(entity.getTotalPrice())
                .paymentMethod(entity.getPaymentMethod())
                .status(entity.getStatus())
                .tenantEmail(entity.getTenantEmail())
                .ownerEmail(entity.getOwnerEmail())
                .advertisementId(entity.getAdvertisementId())
                .ownerConfirmation(entity.getOwnerConfirmation())
                .build();
    }
}