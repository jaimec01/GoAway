package com.tfg.GoAway.modules.advertisement.infrastructure.out.db.sql_server;

import com.tfg.GoAway.modules.advertisement.domain.Advertisement;
import com.tfg.GoAway.modules.advertisement.domain.AdvertisementCategory;
import com.tfg.GoAway.modules.advertisement.domain.AdvertisementCondition;

public class AdvertisementRepositoryMapper {

    public static AdvertisementEntity advertisementToEntity(Advertisement advertisement) {
        AdvertisementEntity entity = new AdvertisementEntity();
        entity.setId(advertisement.getId()); 
        entity.setDescription(advertisement.getDescription());
        entity.setFurnitureCategory(advertisement.getAdvertisementCategory().name());
        entity.setPhotoUrls(advertisement.getPhotoUrls());
        entity.setFurnitureCondition(advertisement.getAdvertisementCondition().name());
        entity.setUserEmail(advertisement.getUserEmail());
        entity.setPrice(advertisement.getPrice());
        return entity;
    }
    
    public static Advertisement entityToAdvertisement(AdvertisementEntity entity) {
        return Advertisement.builder()
                .id(entity.getId()) 
                .description(entity.getDescription())
                .advertisementCategory(AdvertisementCategory.valueOf(entity.getFurnitureCategory()))
                .photoUrls(entity.getPhotoUrls())
                .advertisementCondition(AdvertisementCondition.valueOf(entity.getFurnitureCondition()))
                .userEmail(entity.getUserEmail())
                .price(entity.getPrice())
                .build();
    }
    
}
