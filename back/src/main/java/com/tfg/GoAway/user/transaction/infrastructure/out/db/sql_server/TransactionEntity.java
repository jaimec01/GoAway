package com.tfg.GoAway.user.transaction.infrastructure.out.db.sql_server;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import com.tfg.GoAway.user.advertisement.infrastructure.out.db.sql_server.AdvertisementEntity;
import com.tfg.GoAway.user.transaction.domain.TransactionOwnerConfirmation;
import com.tfg.GoAway.user.transaction.domain.TransactionStatus;

import java.time.LocalDate;
import java.time.LocalDateTime;

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

    @ManyToOne
    @JoinColumn(name = "advertisement_id", referencedColumnName = "id", nullable = false)
    private AdvertisementEntity advertisement;

    @Enumerated(EnumType.STRING)
    @Column(name = "owner_confirmation", nullable = false)
    private TransactionOwnerConfirmation ownerConfirmation = TransactionOwnerConfirmation.PENDING;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }

}