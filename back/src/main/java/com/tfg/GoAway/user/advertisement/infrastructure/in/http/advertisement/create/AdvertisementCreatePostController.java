package com.tfg.GoAway.user.advertisement.infrastructure.in.http.advertisement.create;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tfg.GoAway.user.advertisement.application.advertisement.created.AdvertisementCreate;
import com.tfg.GoAway.user.advertisement.application.advertisement.created.AdvertisementCreateRecord;
import com.tfg.GoAway.user.advertisement.application.advertisement.created.AdvertisementCreateResponse;
import com.tfg.GoAway.user.shared.security.SecurityUtils;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/advertisements")
public class AdvertisementCreatePostController {
 
    private final AdvertisementCreate advertisementCreate;
    private final AdvertisementCreatePostMapper requestMapper;

    @PostMapping
    public AdvertisementCreateResponse saveAdvertisement(@RequestBody AdvertisementCreatePostRequest request) {

        String userEmail = SecurityUtils.getUserEmailFromContext();

        AdvertisementCreateRecord record = requestMapper.toRecord(request, userEmail);

        return advertisementCreate.execute(record);

    }
}
