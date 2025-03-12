package com.tfg.GoAway.user.transaction.domain;


import java.time.LocalDate;
import java.time.LocalDateTime;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Builder
@Data
@Getter
@Setter
public class Transaction {

    private final String id;
    
    private final LocalDate startDate;

    private final LocalDate endDate;

    private Double totalPrice;

    private String paymentMethod;

    private TransactionStatus status;

    private String tenantEmail;

    private String ownerEmail;

    private final String advertisementId;

    private TransactionOwnerConfirmation ownerConfirmation;

    private LocalDateTime createdAt;
    
    private LocalDateTime updatedAt; 

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Transaction other = (Transaction) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        return true;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        return result;
    }

}
