package com.tfg.GoAway.user.advertisement.application.advertisement.finder_by_user;

import java.time.LocalDateTime;

import lombok.Builder;
import lombok.Data;

@Data
@Builder

public class AdvertisementFinderByUserResponse {

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

    private Boolean active;

}
