package com.tfg.GoAway.modules.favorite.domain;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class FavoriteAdvertisement {

    private final String userEmail;
    
    private final String advertisementId;
}
