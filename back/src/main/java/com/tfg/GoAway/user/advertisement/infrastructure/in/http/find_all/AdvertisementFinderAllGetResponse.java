package com.tfg.GoAway.user.advertisement.infrastructure.in.http.find_all;

import java.time.LocalDateTime;
import java.util.List;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AdvertisementFinderAllGetResponse {

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

    private Boolean active;
    
}