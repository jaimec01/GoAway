package com.tfg.GoAway.user.advertisement.application.advertisement.created;

import java.util.UUID;

import com.tfg.GoAway.user.advertisement.domain.Advertisement;
import com.tfg.GoAway.user.advertisement.domain.AdvertisementCategory;
import com.tfg.GoAway.user.advertisement.domain.AdvertisementCondition;

public class AdvertisementCreateMapper {

    public static Advertisement toDomain(AdvertisementCreateRecord record) {
        return Advertisement.builder()
                .id(UUID.randomUUID().toString())
                .title(record.getTitle())
                .description(record.getDescription())
                .advertisementCategory(AdvertisementCategory.fromValue(record.getCategory()))  
                .advertisementCondition(AdvertisementCondition.fromValue(record.getCondition())) 
                .photoUrls(record.getPhotoUrls())
                .price(record.getPrice())
                .userEmail(record.getUserEmail())
                .build();
    }
}