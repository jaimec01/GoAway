package com.tfg.GoAway.modules.advertisement.infrastructure.in.http.advertisement.create;

import com.tfg.GoAway.modules.advertisement.domain.Advertisement;
import com.tfg.GoAway.modules.advertisement.domain.AdvertisementCategory;
import com.tfg.GoAway.modules.advertisement.domain.AdvertisementCondition;


import org.springframework.stereotype.Component;

@Component
public class AdvertisementCreatePostMapper {

    public Advertisement toDomain(AdvertisementCreatePostRequest request, String userEmail) {
        return Advertisement.builder()
                .title(request.getTitle())
                .description(request.getDescription())
                .advertisementCategory(AdvertisementCategory.valueOf(request.getCategory().toUpperCase()))
                .photoUrls(request.getPhotoUrls())
                .advertisementCondition(AdvertisementCondition.valueOf(request.getCondition().toUpperCase()))
                .userEmail(userEmail)
                .price(request.getPrice())
                .build();
    }
}
