package com.tfg.GoAway.user.advertisement.infrastructure.in.http.find_by_user;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tfg.GoAway.shared.security.SecurityUtils;
import com.tfg.GoAway.user.advertisement.application.finder_by_user.AdvertisementFinderByUser;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/advertisements")
public class AdvertisementFinderByUserController {

    private final AdvertisementFinderByUser advertisementFinderByUser;

    @GetMapping("/myAdvertisements")
    public List<AdvertisementFinderByUserGetResponse> getAdvertisementsByUser() {

        String userEmail = SecurityUtils.getUserEmailFromContext();

        List<AdvertisementFinderByUserGetResponse> advertisements = advertisementFinderByUser.findByUser(userEmail);

        return advertisements;
    }
}