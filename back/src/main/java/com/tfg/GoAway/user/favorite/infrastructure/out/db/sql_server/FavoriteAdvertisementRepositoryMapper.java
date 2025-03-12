package com.tfg.GoAway.user.favorite.infrastructure.out.db.sql_server;

import com.tfg.GoAway.user.favorite.domain.FavoriteAdvertisement;

public class FavoriteAdvertisementRepositoryMapper {

    public static FavoriteAdvertisementEntity toEntity(FavoriteAdvertisement favorite) {
        return new FavoriteAdvertisementEntity(
                favorite.getUserEmail(),
                favorite.getAdvertisementId()
        );
    }

    public static FavoriteAdvertisement toDomain(FavoriteAdvertisementEntity entity) {
        return FavoriteAdvertisement.builder()
                .userEmail(entity.getId().getUserEmail())
                .advertisementId(entity.getId().getAdvertisementId())
                .build();
    }

}
