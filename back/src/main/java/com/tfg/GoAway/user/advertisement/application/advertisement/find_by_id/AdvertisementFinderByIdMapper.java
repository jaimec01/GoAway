package com.tfg.GoAway.user.advertisement.application.advertisement.find_by_id;

import org.springframework.stereotype.Component;

import com.tfg.GoAway.user.advertisement.domain.Advertisement;

import java.util.stream.Collectors;

@Component
public class AdvertisementFinderByIdMapper {

    public AdvertisementFinderByIdResponse toResponse(Advertisement advertisement) {
        return AdvertisementFinderByIdResponse.builder()
                .id(advertisement.getId())
                .title(advertisement.getTitle())
                .description(advertisement.getDescription())
                .advertisementCategory(advertisement.getAdvertisementCategory().getValue())
                .photoUrls(advertisement.getPhotos().stream()
                        .map(photo -> AdvertisementFinderByIdPhotoResponse.builder()
                                .id(photo.getId())
                                .photoUrl(photo.getPhotoUrl())
                                .build())
                        .collect(Collectors.toList()))
                .advertisementCondition(advertisement.getAdvertisementCondition().getValue())
                .userEmail(advertisement.getUserEmail())
                .price(advertisement.getPrice())
                .createdAt(advertisement.getCreatedAt())
                .updatedAt(advertisement.getUpdatedAt())
                .active(advertisement.getActive())
                .build();
    }
}