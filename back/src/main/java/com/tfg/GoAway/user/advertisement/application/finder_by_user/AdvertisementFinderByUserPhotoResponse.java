package com.tfg.GoAway.user.advertisement.application.finder_by_user;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AdvertisementFinderByUserPhotoResponse {

    private String id;
    
    private String photoUrl;

}
