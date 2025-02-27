package com.tfg.GoAway.modules.advertisement.application.advertisement.update;

import com.tfg.GoAway.modules.advertisement.domain.Advertisement;
import com.tfg.GoAway.modules.advertisement.domain.AdvertisementCategory;
import com.tfg.GoAway.modules.advertisement.domain.AdvertisementCondition;

public class AdvertisementUpdateMapper {

    public static Advertisement mergeChanges(Advertisement existingAdvertisement, AdvertisementUpdateRecord record) {
        return Advertisement.builder()
                .id(existingAdvertisement.getId())
                .title(record.getTitle() != null ? record.getTitle() : existingAdvertisement.getTitle())
                .description(record.getDescription() != null ? record.getDescription() : existingAdvertisement.getDescription())
                .advertisementCategory(record.getCategory() != null
                        ? AdvertisementCategory.fromValue(record.getCategory())
                        : existingAdvertisement.getAdvertisementCategory())
                .photoUrls(record.getPhotoUrls() != null ? record.getPhotoUrls() : existingAdvertisement.getPhotoUrls())
                .advertisementCondition(record.getCondition() != null
                        ? AdvertisementCondition.fromValue(record.getCondition()) 
                        : existingAdvertisement.getAdvertisementCondition())
                .userEmail(existingAdvertisement.getUserEmail()) 
                .price(record.getPrice() != null ? record.getPrice() : existingAdvertisement.getPrice())
                .createdAt(existingAdvertisement.getCreatedAt()) 
                .updatedAt(existingAdvertisement.getUpdatedAt()) 
                .build();
    }
}