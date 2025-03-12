package com.tfg.GoAway.user.advertisement.domain;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Builder
@Data
@Getter
@Setter
public class AdvertisementPhoto {

    private final String id; 

    private final String advertisementId;

    private final String userEmail; 
    
    private final String photoUrl;

}