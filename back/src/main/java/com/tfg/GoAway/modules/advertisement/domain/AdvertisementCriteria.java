package com.tfg.GoAway.modules.advertisement.domain;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class AdvertisementCriteria {

    private String furnitureCategory;

    private String furnitureCondition;
    
    private String userEmail;

}
