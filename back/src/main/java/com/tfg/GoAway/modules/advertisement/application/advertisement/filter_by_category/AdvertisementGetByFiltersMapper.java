package com.tfg.GoAway.modules.advertisement.application.advertisement.filter_by_category;

import com.tfg.GoAway.modules.advertisement.domain.Advertisement;

public class AdvertisementGetByFiltersMapper {

    public static AdvertisementGetByFiltersResponse toResponse(Advertisement advertisement) {
        return new AdvertisementGetByFiltersResponse(
                advertisement.getId(),
                advertisement.getTitle(),
                advertisement.getDescription(),
                advertisement.getAdvertisementCategory(),
                advertisement.getPhotoUrls(),
                advertisement.getAdvertisementCondition(),
                advertisement.getUserEmail(),
                advertisement.getPrice(),
                advertisement.getCreatedAt()
        );
    }
}
