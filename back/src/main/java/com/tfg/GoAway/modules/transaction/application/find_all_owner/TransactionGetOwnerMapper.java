package com.tfg.GoAway.modules.transaction.application.find_all_owner;

import com.tfg.GoAway.modules.transaction.domain.Transaction;

public class TransactionGetOwnerMapper {

    public static TransactionGetOwnerResponse toResponse(Transaction transaction) {
        return new TransactionGetOwnerResponse(
                transaction.getId(),
                transaction.getStartDate(),
                transaction.getEndDate(),
                transaction.getTotalPrice(),
                transaction.getPaymentMethod(),
                transaction.getStatus(),
                transaction.getTenantEmail(),
                transaction.getAdvertisementId(),
                transaction.getOwnerConfirmation()
        );
    }
}