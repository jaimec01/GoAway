package com.tfg.GoAway.modules.advertisement.infrastructure.in.http.advertisement.update;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AdvertisementUpdateRequest {

    private String id;

    private String title;

    private String description;

    private String category;

    private String photoUrls;

    private String condition;

    private Double price;
}
