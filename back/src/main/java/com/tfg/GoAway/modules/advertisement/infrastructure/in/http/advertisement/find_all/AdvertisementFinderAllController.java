package com.tfg.GoAway.modules.advertisement.infrastructure.in.http.advertisement.find_all;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tfg.GoAway.modules.advertisement.application.advertisement.finder.AdvertisementFinderAll;
import com.tfg.GoAway.modules.advertisement.application.advertisement.finder.AdvertisementFinderAllResponse;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/public/advertisements")
public class AdvertisementFinderAllController {

    @Autowired
    private AdvertisementFinderAll advertisementFinderAll;

    @GetMapping
    public List<AdvertisementFinderAllResponse> getAllAdvertisements() {
        return advertisementFinderAll.finderAll();
    }
}
