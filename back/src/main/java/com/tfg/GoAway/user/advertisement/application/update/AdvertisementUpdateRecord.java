package com.tfg.GoAway.user.advertisement.application.update;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class AdvertisementUpdateRecord {

    private String id;

    private String title;

    private String description;

    private String category;

    private List<MultipartFile> photoUrls; 
    
    private String condition;

    private Double price;
    
    private Boolean active;

    private List<String> existingPhotoIds;
}