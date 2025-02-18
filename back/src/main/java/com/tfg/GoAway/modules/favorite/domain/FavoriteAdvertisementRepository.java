package com.tfg.GoAway.modules.favorite.domain;

import java.util.List;

import com.tfg.GoAway.modules.advertisement.domain.Advertisement;

public interface FavoriteAdvertisementRepository {

    FavoriteAdvertisement save(FavoriteAdvertisement favorite);

    void delete(String advertisementId);

    boolean existsById(String advertisementId);

    List<Advertisement> findFavoritesByUserEmail(String userEmail);

}
