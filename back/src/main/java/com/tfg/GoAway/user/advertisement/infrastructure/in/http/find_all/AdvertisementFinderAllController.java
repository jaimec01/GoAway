package com.tfg.GoAway.user.advertisement.infrastructure.in.http.find_all;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.tfg.GoAway.shared.security.SecurityUtils;
import com.tfg.GoAway.user.advertisement.application.finder.AdvertisementFinderAll;
import com.tfg.GoAway.user.advertisement.application.finder.AdvertisementFinderAllResponse;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/public/advertisements")
public class AdvertisementFinderAllController {

    @Autowired
    private AdvertisementFinderAll advertisementFinderAll;

    @GetMapping
    public List<AdvertisementFinderAllResponse> getAllAdvertisements(
            @RequestParam(required = false) String category,
            @RequestParam(required = false) String condition,
            @RequestParam(required = false, defaultValue = "desc") String sortOrder) {

        String userEmail = null;
        try {
            userEmail = SecurityUtils.getUserEmailFromContext();
        } catch (Exception e) {
            userEmail = null;
        }

        return advertisementFinderAll.finderAll(userEmail, category, condition, sortOrder);
    }
}