package com.tfg.GoAway.user.transaction.infrastructure.out.db.sql_server;

import com.tfg.GoAway.user.advertisement.infrastructure.out.db.sql_server.AdvertisementEntity;
import com.tfg.GoAway.user.transaction.domain.Transaction;

public class TransactionRepositoryMapper {

    public static TransactionEntity transactionToEntity(Transaction transaction, AdvertisementEntity advertisement) {
        TransactionEntity entity = new TransactionEntity();
        entity.setId(transaction.getId());
        entity.setStartDate(transaction.getStartDate());
        entity.setEndDate(transaction.getEndDate());
        entity.setTotalPrice(transaction.getTotalPrice());
        entity.setPaymentMethod(transaction.getPaymentMethod());
        entity.setTenantEmail(transaction.getTenantEmail());
        entity.setOwnerEmail(transaction.getOwnerEmail());
        entity.setAdvertisement(advertisement);
        entity.setOwnerConfirmation(transaction.getOwnerConfirmation());
        entity.setCreatedAt(transaction.getCreatedAt()); 
        entity.setUpdatedAt(transaction.getUpdatedAt()); 
        return entity;
    }

    public static Transaction entityToTransaction(TransactionEntity entity) {
        return Transaction.builder()
                .id(entity.getId())
                .startDate(entity.getStartDate())
                .endDate(entity.getEndDate())
                .totalPrice(entity.getTotalPrice())
                .paymentMethod(entity.getPaymentMethod())
                .tenantEmail(entity.getTenantEmail())
                .ownerEmail(entity.getOwnerEmail())
                .advertisementId(entity.getAdvertisement().getId())
                .ownerConfirmation(entity.getOwnerConfirmation())
                .createdAt(entity.getCreatedAt()) 
                .updatedAt(entity.getUpdatedAt()) 
                .build();
    }
}