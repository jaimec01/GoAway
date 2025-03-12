package com.tfg.GoAway.user.advertisement.application.advertisement.finder;

import org.springframework.stereotype.Component;
import com.tfg.GoAway.user.advertisement.domain.Advertisement;
import java.util.stream.Collectors;

@Component
public class AdvertisementFinderAllMapper {

    public AdvertisementFinderAllResponse toResponse(Advertisement advertisement) {
        return AdvertisementFinderAllResponse.builder()
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
                .isFavorite(false)
                .active(advertisement.getActive())
                .build();
    }
}