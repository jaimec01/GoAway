package com.tfg.GoAway.modules.favorite.infraestructure.in.http;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class FavoriteAdvertisementCreatePostResponse {

    private String userEmail;       

    private String advertisementId; 
    
    private String message;         
}