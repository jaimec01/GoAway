package com.tfg.GoAway.modules.favorite.application;

import org.springframework.stereotype.Service;

import com.tfg.GoAway.modules.favorite.domain.FavoriteAdvertisement;
import com.tfg.GoAway.modules.favorite.domain.FavoriteAdvertisementRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class FavoriteAdvertisementCreate {

    private final FavoriteAdvertisementRepository repository;

    public void addFavorite(String userEmail, String advertisementId) {
        FavoriteAdvertisement favorite = FavoriteAdvertisement.builder()
                .userEmail(userEmail)
                .advertisementId(advertisementId)
                .build();
        repository.save(favorite);
    }

}
