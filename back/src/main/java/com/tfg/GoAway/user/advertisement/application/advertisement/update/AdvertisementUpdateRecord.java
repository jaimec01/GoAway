package com.tfg.GoAway.user.advertisement.application.advertisement.update;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class AdvertisementUpdateRecord {

    private String id;

    private String title;

    private String description;

    private String category;

    private String photoUrls;

    private String condition;

    private Double price;

    private Boolean active;
}
