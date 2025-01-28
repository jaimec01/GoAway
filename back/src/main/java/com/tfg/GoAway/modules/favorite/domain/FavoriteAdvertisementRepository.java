package com.tfg.GoAway.modules.favorite.domain;


public interface FavoriteAdvertisementRepository {

    FavoriteAdvertisement save(FavoriteAdvertisement favorite);

    void delete(String advertisementId);

    boolean existsById(String advertisementId);

    //List<String> findAllByUser(String userEmail);
}
