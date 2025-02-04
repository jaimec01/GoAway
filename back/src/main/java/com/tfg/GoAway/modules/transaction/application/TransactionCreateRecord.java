package com.tfg.GoAway.modules.transaction.application;

import lombok.AllArgsConstructor;
import lombok.Getter;
import java.time.LocalDate;

@Getter
@AllArgsConstructor
public class TransactionCreateRecord {

    private final LocalDate startDate;

    private final LocalDate endDate;

    private final Double totalPrice;

    private final String paymentMethod;

    private final String advertisementId;

    private final String tenantEmail; 
    
    private final String ownerEmail;
}
