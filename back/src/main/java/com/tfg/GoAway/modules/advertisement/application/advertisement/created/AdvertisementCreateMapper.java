package com.tfg.GoAway.modules.advertisement.application.advertisement.created;

import java.util.UUID;

import com.tfg.GoAway.modules.advertisement.domain.Advertisement;
import com.tfg.GoAway.modules.advertisement.domain.AdvertisementCategory;
import com.tfg.GoAway.modules.advertisement.domain.AdvertisementCondition;

public class AdvertisementCreateMapper {

    public static Advertisement toDomain(AdvertisementCreateRecord record) {
        return Advertisement.builder()
                .id(UUID.randomUUID().toString())
                .description(record.getDescription())
                .advertisementCategory(AdvertisementCategory.valueOf(record.getCategory().toUpperCase()))
                .photoUrls(record.getPhotoUrls())
                .advertisementCondition(AdvertisementCondition.valueOf(record.getCondition().toUpperCase()))
                .userEmail(record.getUserEmail())
                .price(record.getPrice())
                .build();
    }
}