package com.tfg.GoAway.modules.advertisement.application.advertisement.finder;

import java.time.LocalDateTime;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AdvertisementFinderAllResponse {

    private String id;

    private String title;

    private String description;

    private String advertisementCategory;

    private String photoUrls;

    private String advertisementCondition;

    private String userEmail;
    
    private Double price;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    private Boolean isFavorite;

}
