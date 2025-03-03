package com.tfg.GoAway.user.advertisement.infrastructure.out.db.sql_server;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import com.tfg.GoAway.user.advertisement.domain.AdvertisementCategory;
import com.tfg.GoAway.user.advertisement.domain.AdvertisementCondition;

import java.time.LocalDateTime;

@Data
@Table(name = "advertisement") 
@Entity
public class AdvertisementEntity {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "id", columnDefinition = "CHAR(36)", updatable = false, nullable = false)
    private String id;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "description", columnDefinition = "TEXT")
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(name = "furniture_category", nullable = false)
    private AdvertisementCategory furnitureCategory;

    @Column(name = "photo_urls", columnDefinition = "TEXT")
    private String photoUrls;

    @Enumerated(EnumType.STRING)
    @Column(name = "furniture_condition", nullable = false)
    private AdvertisementCondition furnitureCondition;

    @Column(name = "user_email")
    private String userEmail;

    @Column(name = "price")
    private Double price;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    @Column(name = "active", nullable = false)
    private Boolean active = true; 

    @PrePersist
    public void prePersist() {
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    public void preUpdate() {
        this.updatedAt = LocalDateTime.now();
    }
}
