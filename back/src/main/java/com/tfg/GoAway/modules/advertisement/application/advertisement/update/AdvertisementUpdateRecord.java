package com.tfg.GoAway.modules.advertisement.application.advertisement.update;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class AdvertisementUpdateRecord {

    private final String id;

    private final String title;

    private final String description;

    private final String category;

    private final String photoUrls;

    private final String condition;

    private final Double price;
}
