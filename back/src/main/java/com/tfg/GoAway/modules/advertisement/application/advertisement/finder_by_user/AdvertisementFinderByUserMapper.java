package com.tfg.GoAway.modules.advertisement.application.advertisement.finder_by_user;

import org.springframework.stereotype.Component;

import com.tfg.GoAway.modules.advertisement.domain.Advertisement;

@Component
public class AdvertisementFinderByUserMapper {

    public AdvertisementFinderByUserResponse toResponse(Advertisement advertisement) {
        return AdvertisementFinderByUserResponse.builder()
                .id(advertisement.getId())
                .title(advertisement.getTitle())
                .description(advertisement.getDescription())
                .advertisementCategory(advertisement.getAdvertisementCategory())
                .photoUrls(advertisement.getPhotoUrls())
                .advertisementCondition(advertisement.getAdvertisementCondition())
                .userEmail(advertisement.getUserEmail())
                .price(advertisement.getPrice())
                .createdAt(advertisement.getCreatedAt())
                .build();
    }
}