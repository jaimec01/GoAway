package com.tfg.GoAway.user.advertisement.application.created;

import com.tfg.GoAway.user.advertisement.domain.Advertisement;
import com.tfg.GoAway.user.advertisement.domain.AdvertisementCategory;
import com.tfg.GoAway.user.advertisement.domain.AdvertisementCondition;
import com.tfg.GoAway.user.advertisement.infrastructure.out.db.sql_server.AdvertisementEntity;

import java.time.LocalDateTime;

import org.springframework.stereotype.Component;

@Component
public class AdvertisementCreateMapper {

    public Advertisement toDomainWithoutPhotos(AdvertisementCreateRecord record) {
        return Advertisement.builder()
                .id(null)
                .title(record.getTitle())
                .description(record.getDescription())
                .advertisementCategory(AdvertisementCategory.fromValue(record.getCategory()))
                .advertisementCondition(AdvertisementCondition.fromValue(record.getCondition()))
                .price(record.getPrice())
                .userEmail(record.getUserEmail())
                // De momento sin fotos
                .photos(null)
                .build();
    }

    public AdvertisementEntity toEntityWithoutPhotos(AdvertisementCreateRecord record) {
        AdvertisementEntity entity = new AdvertisementEntity();
        entity.setId(null);  // se generará con UUID
        entity.setTitle(record.getTitle());
        entity.setDescription(record.getDescription());
        entity.setFurnitureCategory(AdvertisementCategory.fromValue(record.getCategory()));
        entity.setFurnitureCondition(AdvertisementCondition.fromValue(record.getCondition()));
        entity.setPrice(record.getPrice());
        entity.setUserEmail(record.getUserEmail());

        // Manejo de timestamps y active si quieres
        entity.setCreatedAt(LocalDateTime.now());
        entity.setUpdatedAt(LocalDateTime.now());
        entity.setActive(true);

        // photos = null o vacías
        entity.setPhotos(null);

        return entity;
    }
}