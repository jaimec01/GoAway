package com.tfg.GoAway.user.advertisement.infrastructure.in.http.advertisement.create;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import com.tfg.GoAway.user.advertisement.application.advertisement.created.AdvertisementCreateRecord;


import java.util.List;


@Component
public class AdvertisementCreatePostMapper {

    public AdvertisementCreateRecord toRecord(
            AdvertisementCreatePostRequest request,
            String userEmail,
            List<MultipartFile> photos
    ) {
        return AdvertisementCreateRecord.builder()
                .title(request.getTitle())
                .description(request.getDescription())
                .category(request.getCategory())
                .condition(request.getCondition())
                .price(request.getPrice())
                .userEmail(userEmail)
                .photoUrls(photos) 
                .build();
    }
}
