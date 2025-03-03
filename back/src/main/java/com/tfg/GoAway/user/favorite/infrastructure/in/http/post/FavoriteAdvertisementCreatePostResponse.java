package com.tfg.GoAway.user.favorite.infrastructure.in.http.post;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class FavoriteAdvertisementCreatePostResponse {

    private String userEmail;       

    private String advertisementId; 
    
    private String message;         
}