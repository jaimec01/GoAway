package com.tfg.GoAway.user.advertisement.infrastructure.in.http.advertisement.create;

import org.springframework.stereotype.Component;

import com.tfg.GoAway.user.advertisement.application.advertisement.created.AdvertisementCreateRecord;

@Component
public class AdvertisementCreatePostMapper {

    public AdvertisementCreateRecord toRecord(AdvertisementCreatePostRequest request, String userEmail) {
        return AdvertisementCreateRecord.builder()
        .title(request.getTitle())
        .description(request.getDescription())
        .category(request.getCategory())
        .condition(request.getCondition())
        .price(request.getPrice())
        .photoUrls(request.getPhotoUrls())
        .userEmail(userEmail)
        .build();
    }
}
