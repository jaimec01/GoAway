package com.tfg.GoAway.user.advertisement.infrastructure.in.http.advertisement.find_by_user;

import org.springframework.stereotype.Component;

import com.tfg.GoAway.user.advertisement.application.advertisement.finder_by_user.AdvertisementFinderByUserPhotoResponse;
import com.tfg.GoAway.user.advertisement.domain.Advertisement;

import java.util.stream.Collectors;

@Component
public class AdvertisementFinderByUserGetMapper {

    public AdvertisementFinderByUserGetResponse toResponse(Advertisement advertisement) {
        return AdvertisementFinderByUserGetResponse.builder()
                .id(advertisement.getId())
                .title(advertisement.getTitle())
                .description(advertisement.getDescription())
                .advertisementCategory(advertisement.getAdvertisementCategory().getValue())
                .photoUrls(advertisement.getPhotos().stream()
                        .map(photo -> AdvertisementFinderByUserPhotoResponse.builder()
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