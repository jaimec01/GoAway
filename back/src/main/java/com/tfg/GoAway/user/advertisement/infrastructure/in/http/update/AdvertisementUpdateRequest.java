package com.tfg.GoAway.user.advertisement.infrastructure.in.http.update;

import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AdvertisementUpdateRequest {

    private String title;

    private String description;

    private String category;

    private List<String> photoUrls;
    
    private String condition;

    private Double price;
    
    private boolean active;

    private List<String> existingPhotoIds;
}