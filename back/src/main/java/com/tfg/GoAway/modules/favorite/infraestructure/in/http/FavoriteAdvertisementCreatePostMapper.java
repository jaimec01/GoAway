package com.tfg.GoAway.modules.favorite.infraestructure.in.http;

import org.springframework.stereotype.Component;

import com.tfg.GoAway.modules.favorite.domain.FavoriteAdvertisement;

@Component
public class FavoriteAdvertisementCreatePostMapper {

    public FavoriteAdvertisement toDomain(FavoriteAdvertisementCreatePostRequest request, String userEmail) {
        return FavoriteAdvertisement.builder()
                .userEmail(userEmail)
                .advertisementId(request.getAdvertisementId())
                .build();
    }
}