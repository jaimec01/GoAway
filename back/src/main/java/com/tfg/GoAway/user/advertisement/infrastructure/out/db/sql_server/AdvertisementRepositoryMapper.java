package com.tfg.GoAway.user.advertisement.infrastructure.out.db.sql_server;

import com.tfg.GoAway.user.advertisement.domain.Advertisement;

public class AdvertisementRepositoryMapper {

    public static AdvertisementEntity advertisementToEntity(Advertisement advertisement) {
        AdvertisementEntity entity = new AdvertisementEntity();
        entity.setId(advertisement.getId()); 
        entity.setTitle(advertisement.getTitle());
        entity.setDescription(advertisement.getDescription());
        entity.setFurnitureCategory(advertisement.getAdvertisementCategory()); 
        entity.setPhotoUrls(advertisement.getPhotoUrls());
        entity.setFurnitureCondition(advertisement.getAdvertisementCondition()); 
        entity.setUserEmail(advertisement.getUserEmail());
        entity.setPrice(advertisement.getPrice());
        entity.setCreatedAt(advertisement.getCreatedAt());
        entity.setUpdatedAt(advertisement.getUpdatedAt());
        entity.setActive(advertisement.getActive());
        return entity;
    }
    
    public static Advertisement entityToAdvertisement(AdvertisementEntity entity) {
        return Advertisement.builder()
                .id(entity.getId()) 
                .title(entity.getTitle())
                .description(entity.getDescription())
                .advertisementCategory(entity.getFurnitureCategory()) 
                .photoUrls(entity.getPhotoUrls())
                .advertisementCondition(entity.getFurnitureCondition()) 
                .userEmail(entity.getUserEmail())
                .price(entity.getPrice())
                .createdAt(entity.getCreatedAt())
                .updatedAt(entity.getUpdatedAt())
                .isFavorite(false)
                .active(entity.getActive())
                .build();
    }
    
}
