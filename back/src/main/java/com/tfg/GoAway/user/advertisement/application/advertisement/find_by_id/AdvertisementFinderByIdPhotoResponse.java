package com.tfg.GoAway.user.advertisement.application.advertisement.find_by_id;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AdvertisementFinderByIdPhotoResponse {

    private String id;
    private String photoUrl;

}
