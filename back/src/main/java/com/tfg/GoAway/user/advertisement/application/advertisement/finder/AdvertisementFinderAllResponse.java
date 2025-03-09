package com.tfg.GoAway.user.advertisement.application.advertisement.finder;

import java.time.LocalDateTime;
import java.util.List;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AdvertisementFinderAllResponse {

    private String id;

    private String title;

    private String description;

    private String advertisementCategory;

    private List<String> photoUrls;

    private String advertisementCondition;

    private String userEmail;

    private Double price;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    private Boolean isFavorite;

    private Boolean active;
    
}