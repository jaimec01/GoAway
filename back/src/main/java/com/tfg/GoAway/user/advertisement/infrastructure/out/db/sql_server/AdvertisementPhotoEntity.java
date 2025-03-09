package com.tfg.GoAway.user.advertisement.infrastructure.out.db.sql_server;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import org.hibernate.annotations.GenericGenerator;

@Data
@Entity
@Table(name = "advertisement_photos")
@Builder
@NoArgsConstructor              
@AllArgsConstructor    
public class AdvertisementPhotoEntity {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "id", columnDefinition = "CHAR(36)", updatable = false, nullable = false)
    private String id;

    @Column(name = "user_email", nullable = false)
    private String userEmail;

    @Column(name = "photo_url", nullable = false)
    private String photoUrl;

    @ManyToOne
    @JoinColumn(name = "advertisement_id")
    private AdvertisementEntity advertisement;
}