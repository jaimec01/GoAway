package com.tfg.GoAway.modules.advertisement.infrastructure.in.http.advertisement.update;

import com.tfg.GoAway.modules.advertisement.domain.Advertisement;
import com.tfg.GoAway.modules.advertisement.domain.AdvertisementCategory;
import com.tfg.GoAway.modules.advertisement.domain.AdvertisementCondition;
import org.springframework.stereotype.Component;

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
                .build();
    }
}
