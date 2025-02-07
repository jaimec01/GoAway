package com.tfg.GoAway.modules.favorite.infrastructure.out.db.sql_server;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
@Table(name = "user_favorites_advertisement")
public class FavoriteAdvertisementEntity {

    @EmbeddedId
    private FavoriteAdvertisementId id; 

    public FavoriteAdvertisementEntity(String userEmail, String advertisementId) {
        this.id = new FavoriteAdvertisementId(userEmail, advertisementId);
    }
}
