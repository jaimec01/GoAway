package com.tfg.GoAway.modules.transaction.application.find_all_owner;

import com.tfg.GoAway.modules.transaction.domain.TransactionOwnerConfirmation;
import com.tfg.GoAway.modules.transaction.domain.TransactionStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDate;

@Getter
@AllArgsConstructor
public class TransactionGetOwnerResponse {

    private final String id;

    private final LocalDate startDate;

    private final LocalDate endDate;

    private final Double totalPrice;

    private final String paymentMethod;

    private final TransactionStatus status;

    private final String tenantEmail;

    private final String advertisementId;

    private final TransactionOwnerConfirmation ownerConfirmation;
}