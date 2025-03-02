package com.tfg.GoAway.user.transaction.infrastructure.in.http.create;

import org.springframework.stereotype.Component;

import com.tfg.GoAway.user.transaction.application.create.TransactionCreateRecord;

@Component
public class TransactionCreatePostMapper {

    public TransactionCreateRecord toRecord(TransactionCreatePostRequest request, String tenantEmail) {
        return new TransactionCreateRecord(
                request.getStartDate(),
                request.getEndDate(),
                request.getTotalPrice(),
                request.getPaymentMethod(),
                request.getAdvertisementId(),
                tenantEmail
                );
    }
}
