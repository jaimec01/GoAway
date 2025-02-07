package com.tfg.GoAway.modules.transaction.application.find_all_tenant;

import com.tfg.GoAway.modules.transaction.domain.TransactionStatus;
import com.tfg.GoAway.modules.transaction.domain.TransactionOwnerConfirmation;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDate;

@Getter
@AllArgsConstructor
public class TransactionGetTenantResponse {

    private final String id;

    private final LocalDate startDate;

    private final LocalDate endDate;

    private final Double totalPrice;

    private final String paymentMethod;

    private final TransactionStatus status;

    private final String ownerEmail;

    private final String advertisementId;

    private final TransactionOwnerConfirmation ownerConfirmation;
}