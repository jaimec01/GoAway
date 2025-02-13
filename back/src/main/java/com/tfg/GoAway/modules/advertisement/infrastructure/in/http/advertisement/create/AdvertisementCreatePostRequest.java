package com.tfg.GoAway.modules.advertisement.infrastructure.in.http.advertisement.create;

import lombok.Data;

@Data
public class AdvertisementCreatePostRequest {

    private String title;

    private String description;

    private String category;

    private String photoUrls;

    private String condition;

    private String userEmail;

    private Double price;

}
