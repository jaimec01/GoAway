package com.tfg.GoAway.user.advertisement.infrastructure.in.http.advertisement.create;

import java.util.List;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

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

    @PostMapping(consumes = "multipart/form-data") 
    public AdvertisementCreateResponse saveAdvertisement(
            @RequestPart("advertisement") AdvertisementCreatePostRequest request,
            @RequestPart("photos") List<MultipartFile> photos) {

        String userEmail = SecurityUtils.getUserEmailFromContext();

        AdvertisementCreateRecord record = requestMapper.toRecord(request, userEmail, photos);

        return advertisementCreate.execute(record);
    }
}