package com.tfg.GoAway.user.transaction.application.find_all_tenant;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalDateTime;

import com.tfg.GoAway.user.transaction.domain.TransactionOwnerConfirmation;

@Getter
@AllArgsConstructor
public class TransactionGetTenantResponse {

    private final String id;

    private final LocalDate startDate;

    private final LocalDate endDate;

    private final Double totalPrice;

    private final String paymentMethod;

    private final String ownerEmail;

    private final String advertisementId;

    private final TransactionOwnerConfirmation ownerConfirmation;
        
    private LocalDateTime createdAt;
    
    private LocalDateTime updatedAt; 
}