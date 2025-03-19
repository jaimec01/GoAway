package com.tfg.GoAway.user.advertisement.application.finder_by_user;

import java.util.stream.Collectors;
import org.springframework.stereotype.Component;
import com.tfg.GoAway.user.advertisement.domain.Advertisement;

@Component
public class AdvertisementFinderByUserMapper {

    public AdvertisementFinderByUserResponse toResponse(Advertisement advertisement) {
        return AdvertisementFinderByUserResponse.builder()
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