package com.tfg.GoAway.user.advertisement.application.advertisement.find_by_id;

import java.time.LocalDateTime;
import java.util.List;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AdvertisementFinderByIdResponse {

    private String id;

    private String title;

    private String description;

    private String advertisementCategory;

    private List<AdvertisementFinderByIdPhotoResponse> photoUrls; 
    
    private String advertisementCondition;

    private String userEmail;

    private Double price;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    private Boolean active;
    
}