package com.tfg.GoAway.user.advertisement.infrastructure.in.http.advertisement.update;

import org.springframework.stereotype.Component;

import com.tfg.GoAway.user.advertisement.domain.Advertisement;
import com.tfg.GoAway.user.advertisement.domain.AdvertisementCategory;
import com.tfg.GoAway.user.advertisement.domain.AdvertisementCondition;

@Component
public class AdvertisementUpdatePutMapper {

        public Advertisement toDomain(AdvertisementUpdateRequest request, Advertisement existingAdvertisement) {
                return Advertisement.builder()
                                .id(existingAdvertisement.getId())
                                .title(request.getTitle())
                                .description(request.getDescription())
                                .advertisementCategory(
                                                AdvertisementCategory.valueOf(request.getCategory().toUpperCase()))
                                .photos(existingAdvertisement.getPhotos()) 
                                .advertisementCondition(
                                                AdvertisementCondition.valueOf(request.getCondition().toUpperCase()))
                                .userEmail(existingAdvertisement.getUserEmail())
                                .price(request.getPrice())
                                .active(request.isActive())
                                .createdAt(existingAdvertisement.getCreatedAt())
                                .updatedAt(existingAdvertisement.getUpdatedAt())
                                .build();
        }
}