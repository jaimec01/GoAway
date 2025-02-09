package com.tfg.GoAway.modules.advertisement.infrastructure.in.http.advertisement.filter_by_category;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.tfg.GoAway.modules.advertisement.application.advertisement.filter_by_category.AdvertisementGetByFilters;
import com.tfg.GoAway.modules.advertisement.application.advertisement.filter_by_category.AdvertisementGetByFiltersResponse;


import lombok.RequiredArgsConstructor;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/public/advertisements")
public class AdvertisementGetByFiltersController {

    @Autowired
    private final AdvertisementGetByFilters advertisementGetByFilters;

    @GetMapping("/filter")
    public ResponseEntity<List<AdvertisementGetByFiltersResponse>> getAdvertisementsByFilters(
            @RequestParam(required = false) String category,
            @RequestParam(required = false) String condition) {

        List<AdvertisementGetByFiltersResponse> advertisements = advertisementGetByFilters.execute(category, condition);
        return ResponseEntity.ok(advertisements);
    }
}
