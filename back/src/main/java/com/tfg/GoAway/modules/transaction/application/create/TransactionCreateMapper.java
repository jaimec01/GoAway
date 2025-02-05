package com.tfg.GoAway.modules.transaction.application.create;

import java.util.UUID;
import com.tfg.GoAway.modules.transaction.domain.Transaction;
import com.tfg.GoAway.modules.transaction.domain.TransactionStatus;
import com.tfg.GoAway.modules.transaction.domain.TransactionOwnerConfirmation;

public class TransactionCreateMapper {

    public static Transaction toDomain(TransactionCreateRecord record) {
        return Transaction.builder()
                .id(UUID.randomUUID().toString())
                .startDate(record.getStartDate())
                .endDate(record.getEndDate())
                .totalPrice(record.getTotalPrice())
                .paymentMethod(record.getPaymentMethod())
                .status(TransactionStatus.PENDING) // Siempre comienza en PENDING
                .tenantEmail(record.getTenantEmail()) // Usuario autenticado
                .ownerEmail(record.getOwnerEmail()) // Viene del request
                .advertisementId(record.getAdvertisementId())
                .ownerConfirmation(TransactionOwnerConfirmation.PENDING)
                .build();
    }
}
