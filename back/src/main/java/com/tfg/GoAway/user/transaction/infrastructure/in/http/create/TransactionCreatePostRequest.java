package com.tfg.GoAway.user.transaction.infrastructure.in.http.create;

import lombok.Getter;
import lombok.Setter;
import java.time.LocalDate;

@Getter
@Setter
public class TransactionCreatePostRequest {

    private LocalDate startDate;

    private LocalDate endDate;

    private Double totalPrice;

    private String paymentMethod;

    private String advertisementId;
    
}
