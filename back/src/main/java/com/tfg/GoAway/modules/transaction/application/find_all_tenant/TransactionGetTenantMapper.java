package com.tfg.GoAway.modules.transaction.application.find_all_tenant;

import com.tfg.GoAway.modules.transaction.domain.Transaction;
import com.tfg.GoAway.modules.transaction.domain.TransactionOwnerConfirmation;

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
                transaction.getOwnerConfirmation()
        );
    }

    private static boolean shouldShowOwnerEmail(Transaction transaction) {
        return transaction.getOwnerConfirmation() == TransactionOwnerConfirmation.ACCEPTED;
    }
}
