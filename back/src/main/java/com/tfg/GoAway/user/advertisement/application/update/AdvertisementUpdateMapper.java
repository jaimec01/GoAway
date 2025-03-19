package com.tfg.GoAway.user.advertisement.application.update;

import com.tfg.GoAway.user.advertisement.domain.Advertisement;
import com.tfg.GoAway.user.advertisement.domain.AdvertisementCategory;
import com.tfg.GoAway.user.advertisement.domain.AdvertisementCondition;
import com.tfg.GoAway.user.advertisement.domain.AdvertisementPhoto;
import java.util.List;

public class AdvertisementUpdateMapper {

    public static Advertisement mergeChanges(Advertisement existingAdvertisement, AdvertisementUpdateRecord record, List<AdvertisementPhoto> photosToKeep) {
        return Advertisement.builder()
                .id(existingAdvertisement.getId())
                .title(record.getTitle() != null ? record.getTitle() : existingAdvertisement.getTitle())
                .description(record.getDescription() != null ? record.getDescription() : existingAdvertisement.getDescription())
                .advertisementCategory(record.getCategory() != null
                        ? AdvertisementCategory.fromValue(record.getCategory())
                        : existingAdvertisement.getAdvertisementCategory())
                .photos(photosToKeep)
                .advertisementCondition(record.getCondition() != null
                        ? AdvertisementCondition.fromValue(record.getCondition())
                        : existingAdvertisement.getAdvertisementCondition())
                .userEmail(existingAdvertisement.getUserEmail())
                .price(record.getPrice() != null ? record.getPrice() : existingAdvertisement.getPrice())
                .createdAt(existingAdvertisement.getCreatedAt())
                .updatedAt(existingAdvertisement.getUpdatedAt())
                .active(record.getActive() != null ? record.getActive() : existingAdvertisement.getActive())
                .build();
    }
}