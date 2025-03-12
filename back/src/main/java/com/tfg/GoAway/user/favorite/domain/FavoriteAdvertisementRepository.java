package com.tfg.GoAway.user.favorite.domain;

import java.util.List;

import com.tfg.GoAway.user.advertisement.domain.Advertisement;

public interface FavoriteAdvertisementRepository {

    FavoriteAdvertisement save(FavoriteAdvertisement favorite);

    void delete(String advertisementId);

    boolean existsById(String advertisementId);

    List<Advertisement> findFavoritesByUserEmail(String userEmail);

}
