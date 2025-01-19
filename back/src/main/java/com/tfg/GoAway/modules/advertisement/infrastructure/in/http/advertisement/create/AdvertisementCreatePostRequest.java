package com.tfg.GoAway.modules.advertisement.infrastructure.in.http.advertisement.create;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AdvertisementCreatePostRequest {

    private String description;

    private String category;

    private String photoUrls;

    private String condition;

    private String userEmail;

    private Double price;
}
