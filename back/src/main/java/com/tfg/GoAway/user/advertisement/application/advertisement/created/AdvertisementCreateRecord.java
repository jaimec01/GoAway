package com.tfg.GoAway.user.advertisement.application.advertisement.created;

import lombok.Builder;
import lombok.Data;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

@Builder
@Data
public class AdvertisementCreateRecord {

    private final String title;

    private final String description;

    private final String category;

    private final List<MultipartFile> photoUrls; 

    private final String condition;

    private final String userEmail;
    
    private final Double price;
}