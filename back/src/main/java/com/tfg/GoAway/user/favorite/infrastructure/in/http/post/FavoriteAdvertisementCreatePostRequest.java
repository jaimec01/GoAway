package com.tfg.GoAway.user.favorite.infrastructure.in.http.post;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FavoriteAdvertisementCreatePostRequest {

    private String advertisementId; // ID del anuncio que se marcará como favorito
}
