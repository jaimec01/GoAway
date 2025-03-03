package com.tfg.GoAway.user.favorite.infrastructure.in.http.post;

import org.springframework.stereotype.Component;

import com.tfg.GoAway.user.favorite.domain.FavoriteAdvertisement;

@Component
public class FavoriteAdvertisementCreatePostMapper {

    public FavoriteAdvertisement toDomain(FavoriteAdvertisementCreatePostRequest request, String userEmail) {
        return FavoriteAdvertisement.builder()
                .userEmail(userEmail)
                .advertisementId(request.getAdvertisementId())
                .build();
    }
}