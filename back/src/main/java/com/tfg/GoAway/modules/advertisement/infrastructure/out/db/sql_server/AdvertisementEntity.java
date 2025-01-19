package com.tfg.GoAway.modules.advertisement.infrastructure.out.db.sql_server;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;


@Data
@Table(name = "advertisement") 
@Entity
public class AdvertisementEntity {
 
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "id", columnDefinition = "CHAR(36)", updatable = false, nullable = false)
    private String id;

    @Column(name = "description", columnDefinition = "TEXT")
    private String description;

    @Column(name = "furniture_category", nullable = false)
    private String furnitureCategory;

    @Column(name = "photo_urls", columnDefinition = "TEXT")
    private String photoUrls;

    @Column(name = "furniture_condition", nullable = false)
    private String furnitureCondition;

    @Column(name = "user_email")
    private String userEmail;

    @Column(name = "price")
    private Double price;
}
