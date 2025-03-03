package com.tfg.GoAway.user.advertisement.infrastructure.in.http.advertisement.update;

import org.springframework.stereotype.Component;

import com.tfg.GoAway.user.advertisement.domain.Advertisement;
import com.tfg.GoAway.user.advertisement.domain.AdvertisementCategory;
import com.tfg.GoAway.user.advertisement.domain.AdvertisementCondition;

@Component
public class AdvertisementUpdateMapper {

    public Advertisement toDomain(AdvertisementUpdateRequest request, Advertisement existingAdvertisement) {
        return Advertisement.builder()
                .title(request.getTitle())
                .description(request.getDescription())
                .advertisementCategory(AdvertisementCategory.valueOf(request.getCategory().toUpperCase()))
                .photoUrls(request.getPhotoUrls())
                .advertisementCondition(AdvertisementCondition.valueOf(request.getCondition().toUpperCase()))
                .price(request.getPrice())
                .active(request.isActive())
                .build();
    }
}
