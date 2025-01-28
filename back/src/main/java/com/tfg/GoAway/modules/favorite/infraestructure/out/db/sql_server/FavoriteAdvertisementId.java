package com.tfg.GoAway.modules.favorite.infraestructure.out.db.sql_server;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Objects;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class FavoriteAdvertisementId implements Serializable {

    private String userEmail; // Clave compuesta: ID del usuario
    private String advertisementId; // Clave compuesta: ID del anuncio

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FavoriteAdvertisementId that = (FavoriteAdvertisementId) o;
        return Objects.equals(userEmail, that.userEmail) &&
               Objects.equals(advertisementId, that.advertisementId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userEmail, advertisementId);
    }
}