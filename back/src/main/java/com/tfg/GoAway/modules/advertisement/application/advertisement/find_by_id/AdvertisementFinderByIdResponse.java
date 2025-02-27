package com.tfg.GoAway.modules.advertisement.application.advertisement.find_by_id;

import java.time.LocalDateTime;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AdvertisementFinderByIdResponse {

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
}
