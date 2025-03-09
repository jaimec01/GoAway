package com.tfg.GoAway.user.advertisement.infrastructure.in.http.advertisement.find_by_user;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AdvertisementFinderByUserPhotoGetResponse {

    private String id;

    private String photoUrl;

}
