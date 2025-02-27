package com.tfg.GoAway.modules.advertisement.infrastructure.in.http.advertisement.find_by_id;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.tfg.GoAway.modules.advertisement.application.advertisement.find_by_id.AdvertisementFinderById;
import com.tfg.GoAway.modules.advertisement.application.advertisement.find_by_id.AdvertisementFinderByIdResponse;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/public/advertisements")
public class AdvertisementGetByIdController {

    private final AdvertisementFinderById advertisementFinderById;

    @GetMapping("/{id}")
    public ResponseEntity<AdvertisementFinderByIdResponse> getAdvertisementById(@PathVariable String id) {
        return advertisementFinderById.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}
