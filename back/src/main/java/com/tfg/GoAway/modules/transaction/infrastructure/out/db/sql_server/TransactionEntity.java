package com.tfg.GoAway.modules.transaction.infrastructure.out.db.sql_server;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import com.tfg.GoAway.modules.transaction.domain.TransactionStatus;
import com.tfg.GoAway.modules.transaction.domain.TransactionOwnerConfirmation;

import java.time.LocalDate;

@Data
@Table(name = "transaction")
@Entity
public class TransactionEntity {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "id", columnDefinition = "CHAR(36)", updatable = false, nullable = false)
    private String id;

    @Column(name = "start_date", nullable = false)
    private LocalDate startDate;

    @Column(name = "end_date", nullable = false)
    private LocalDate endDate;

    @Column(name = "total_price")
    private Double totalPrice;

    @Column(name = "payment_method")
    private String paymentMethod;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private TransactionStatus status;

    @Column(name = "tenant_email", nullable = false)
    private String tenantEmail;

    @Column(name = "owner_email", nullable = false)
    private String ownerEmail;

    @Column(name = "advertisement_id", nullable = false, columnDefinition = "CHAR(36)")
    private String advertisementId;

    @Enumerated(EnumType.STRING)
    @Column(name = "owner_confirmation", nullable = false)
    private TransactionOwnerConfirmation ownerConfirmation = TransactionOwnerConfirmation.PENDING;

}