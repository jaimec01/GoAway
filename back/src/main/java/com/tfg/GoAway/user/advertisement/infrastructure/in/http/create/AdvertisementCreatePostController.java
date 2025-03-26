package com.tfg.GoAway.user.advertisement.infrastructure.in.http.create;

import java.util.List;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.tfg.GoAway.shared.security.SecurityUtils;
import com.tfg.GoAway.user.advertisement.application.created.AdvertisementCreate;
import com.tfg.GoAway.user.advertisement.application.created.AdvertisementCreateRecord;
import com.tfg.GoAway.user.advertisement.application.created.AdvertisementCreateResponse;

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
            @RequestPart(value = "photos", required = false) List<MultipartFile> photos) {

        String userEmail = SecurityUtils.getUserEmailFromContext();

        List<MultipartFile> photosToUse = (photos != null && !photos.isEmpty()) ? photos : null;

        AdvertisementCreateRecord record = requestMapper.toRecord(request, userEmail, photosToUse);

        return advertisementCreate.execute(record);
    }
}