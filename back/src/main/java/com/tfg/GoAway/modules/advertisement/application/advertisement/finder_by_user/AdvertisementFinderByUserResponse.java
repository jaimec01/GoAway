package com.tfg.GoAway.modules.advertisement.application.advertisement.finder_by_user;

import java.time.LocalDateTime;

import com.tfg.GoAway.modules.advertisement.domain.AdvertisementCategory;
import com.tfg.GoAway.modules.advertisement.domain.AdvertisementCondition;

import lombok.Builder;
import lombok.Data;

@Data
@Builder

public class AdvertisementFinderByUserResponse {

    private String id;

    private String title;

    private String description;

    private AdvertisementCategory advertisementCategory;

    private String photoUrls;

    private AdvertisementCondition advertisementCondition;

    private String userEmail;
    
    private Double price;

    private LocalDateTime createdAt;
}
