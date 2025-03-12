package com.tfg.GoAway.user.transaction.application.find_all_owner;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalDateTime;

import com.tfg.GoAway.user.transaction.domain.TransactionOwnerConfirmation;
import com.tfg.GoAway.user.transaction.domain.TransactionStatus;

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
    
    private LocalDateTime createdAt;
    
    private LocalDateTime updatedAt; 
}