package com.tfg.GoAway.modules.transaction.infrastructure.in.http.create;

import org.springframework.stereotype.Component;

import com.tfg.GoAway.modules.transaction.application.create.TransactionCreateRecord;

@Component
public class TransactionCreatePostMapper {

    public TransactionCreateRecord toRecord(TransactionCreatePostRequest request, String tenantEmail) {
        return new TransactionCreateRecord(
                request.getStartDate(),
                request.getEndDate(),
                request.getTotalPrice(),
                request.getPaymentMethod(),
                request.getAdvertisementId(),
                tenantEmail, // Usuario autenticado
                request.getOwnerEmail()         
                );
    }
}
