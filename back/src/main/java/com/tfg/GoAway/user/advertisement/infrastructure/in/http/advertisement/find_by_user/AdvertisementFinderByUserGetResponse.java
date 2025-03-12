package com.tfg.GoAway.user.advertisement.infrastructure.in.http.advertisement.find_by_user;

import java.time.LocalDateTime;
import java.util.List;

import com.tfg.GoAway.user.advertisement.application.advertisement.finder_by_user.AdvertisementFinderByUserPhotoResponse;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AdvertisementFinderByUserGetResponse {

    private String id;

    private String title;

    private String description;

    private String advertisementCategory;

    private List<AdvertisementFinderByUserPhotoResponse> photoUrls;

    private String advertisementCondition;

    private String userEmail;

    private Double price;

    private LocalDateTime createdAt;
    
    private LocalDateTime updatedAt;

    private Boolean active;
}