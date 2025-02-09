package com.tfg.GoAway.modules.advertisement.application.advertisement.filter_by_category;

import com.tfg.GoAway.modules.advertisement.domain.AdvertisementCategory;
import com.tfg.GoAway.modules.advertisement.domain.AdvertisementCondition;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class AdvertisementGetByFiltersResponse {

    private final String id;
    private final String title;
    private final String description;
    private final AdvertisementCategory advertisementCategory;
    private final String photoUrls;
    private final AdvertisementCondition advertisementCondition;
    private final String userEmail;
    private final Double price;
    private final LocalDateTime createdAt;
}