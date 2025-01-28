package com.tfg.GoAway.modules.favorite.infraestructure.in.http;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FavoriteAdvertisementCreatePostRequest {

    private String advertisementId; // ID del anuncio que se marcará como favorito
}
