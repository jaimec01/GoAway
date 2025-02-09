package com.tfg.GoAway.modules.advertisement.application.advertisement.created;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class AdvertisementCreateRecord {

    private final String id;

    private final String description;

    private final String category;

    private final String photoUrls;

    private final String condition;

    private final String userEmail;

    private final Double price;
}