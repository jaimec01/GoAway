package com.tfg.GoAway.user.transaction.application.create;

import java.util.UUID;

import com.tfg.GoAway.user.advertisement.domain.Advertisement;
import com.tfg.GoAway.user.transaction.domain.Transaction;
import com.tfg.GoAway.user.transaction.domain.TransactionOwnerConfirmation;
import com.tfg.GoAway.user.transaction.domain.TransactionStatus;

public class TransactionCreateMapper {

    public static Transaction toDomain(TransactionCreateRecord record, Advertisement advertisement) {
        return Transaction.builder()
                .id(UUID.randomUUID().toString())
                .startDate(record.getStartDate())
                .endDate(record.getEndDate())
                .totalPrice(record.getTotalPrice())
                .paymentMethod(record.getPaymentMethod())
                .status(TransactionStatus.PENDING) 
                .tenantEmail(record.getTenantEmail()) 
                .ownerEmail(advertisement.getUserEmail())
                .advertisementId(advertisement.getId())
                .ownerConfirmation(TransactionOwnerConfirmation.PENDING)
                .build();
    }
}
