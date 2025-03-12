package com.tfg.GoAway.user.transaction.application.find_all_tenant;

import com.tfg.GoAway.user.transaction.domain.Transaction;
import com.tfg.GoAway.user.transaction.domain.TransactionOwnerConfirmation;

public class TransactionGetTenantMapper {

    public static TransactionGetTenantResponse toResponse(Transaction transaction) {
        return new TransactionGetTenantResponse(
                transaction.getId(),
                transaction.getStartDate(),
                transaction.getEndDate(),
                transaction.getTotalPrice(),
                transaction.getPaymentMethod(),
                transaction.getStatus(),
                shouldShowOwnerEmail(transaction) ? transaction.getOwnerEmail() : null,
                transaction.getAdvertisementId(),
                transaction.getOwnerConfirmation(),
                transaction.getCreatedAt(),
                transaction.getUpdatedAt()
        );
    }

    private static boolean shouldShowOwnerEmail(Transaction transaction) {
        return transaction.getOwnerConfirmation() == TransactionOwnerConfirmation.ACCEPTED;
    }
}
