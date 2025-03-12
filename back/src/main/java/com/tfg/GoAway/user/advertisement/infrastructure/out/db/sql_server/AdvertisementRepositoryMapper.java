package com.tfg.GoAway.user.advertisement.infrastructure.out.db.sql_server;

import com.tfg.GoAway.user.advertisement.domain.Advertisement;
import com.tfg.GoAway.user.advertisement.domain.AdvertisementPhoto;
import java.util.stream.Collectors;

public class AdvertisementRepositoryMapper {

    public static AdvertisementEntity advertisementToEntity(Advertisement advertisement) {
        AdvertisementEntity entity = new AdvertisementEntity();

        entity.setId(advertisement.getId());
        entity.setTitle(advertisement.getTitle());
        entity.setDescription(advertisement.getDescription());
        entity.setFurnitureCategory(advertisement.getAdvertisementCategory());
        entity.setFurnitureCondition(advertisement.getAdvertisementCondition());
        entity.setUserEmail(advertisement.getUserEmail());
        entity.setPrice(advertisement.getPrice());
        entity.setCreatedAt(advertisement.getCreatedAt());
        entity.setUpdatedAt(advertisement.getUpdatedAt());
        
        if (advertisement.getActive() == null) {
            entity.setActive(true);
        } else {
            entity.setActive(advertisement.getActive());
        }

        if (advertisement.getPhotos() != null) {
            entity.setPhotos(
                advertisement.getPhotos().stream()
                    .map(photo -> {
                        AdvertisementPhotoEntity photoEntity = new AdvertisementPhotoEntity();
                        // photo.getId() suele ser null => JPA generará
                        photoEntity.setId(photo.getId());
                        photoEntity.setPhotoUrl(photo.getPhotoUrl());
                        photoEntity.setUserEmail(photo.getUserEmail());
                        photoEntity.setAdvertisement(entity);

                        return photoEntity;
                    })
                    .collect(Collectors.toList())
            );
        }

        return entity;
    }

    public static Advertisement entityToAdvertisement(AdvertisementEntity entity) {
        Advertisement.AdvertisementBuilder builder = Advertisement.builder()
            .id(entity.getId())
            .title(entity.getTitle())
            .description(entity.getDescription())
            .advertisementCategory(entity.getFurnitureCategory())
            .advertisementCondition(entity.getFurnitureCondition())
            .userEmail(entity.getUserEmail())
            .price(entity.getPrice())
            .createdAt(entity.getCreatedAt())
            .updatedAt(entity.getUpdatedAt())
            .isFavorite(false) 
            .active(entity.getActive());

        if (entity.getPhotos() != null) {
            builder.photos(
                entity.getPhotos().stream()
                    .map(photoEntity -> AdvertisementPhoto.builder()
                        .id(photoEntity.getId())
                        .advertisementId(entity.getId()) 
                        .userEmail(photoEntity.getUserEmail())
                        .photoUrl(photoEntity.getPhotoUrl())
                        .build())
                    .collect(Collectors.toList())
            );
        }

        return builder.build();
    }
}
