package com.tfg.GoAway.user.favorite.application.create;

import org.springframework.stereotype.Service;

import com.tfg.GoAway.user.favorite.domain.FavoriteAdvertisement;
import com.tfg.GoAway.user.favorite.domain.FavoriteAdvertisementRepository;

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
