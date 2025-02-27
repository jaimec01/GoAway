package com.tfg.GoAway.modules.advertisement.application.advertisement.finder;

import org.springframework.stereotype.Component;

import com.tfg.GoAway.modules.advertisement.domain.Advertisement;

@Component
public class AdvertisementFinderAllMapper {

    public AdvertisementFinderAllResponse toResponse(Advertisement advertisement) {
        return AdvertisementFinderAllResponse.builder()
                .id(advertisement.getId())
                .title(advertisement.getTitle())
                .description(advertisement.getDescription())
                .advertisementCategory(advertisement.getAdvertisementCategory().getValue())
                .photoUrls(advertisement.getPhotoUrls())
                .advertisementCondition(advertisement.getAdvertisementCondition().getValue())
                .userEmail(advertisement.getUserEmail())
                .price(advertisement.getPrice())
                .createdAt(advertisement.getCreatedAt())
                .updatedAt(advertisement.getUpdatedAt())
                .isFavorite(false)
                .build();
    }
}
