package com.tfg.GoAway.user.advertisement.infrastructure.in.http.find_all;

import org.springframework.stereotype.Component;
import com.tfg.GoAway.user.advertisement.domain.Advertisement;

import java.util.stream.Collectors;

@Component
public class AdvertisementFinderAllGetMapper {

    public AdvertisementFinderAllGetResponse toResponse(Advertisement advertisement) {
        return AdvertisementFinderAllGetResponse.builder()
                .id(advertisement.getId())
                .title(advertisement.getTitle())
                .description(advertisement.getDescription())
                .advertisementCategory(advertisement.getAdvertisementCategory().getValue())
                .photoUrls(advertisement.getPhotos().stream()
                        .map(photo -> photo.getPhotoUrl())
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