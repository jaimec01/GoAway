package com.tfg.GoAway.user.advertisement.infrastructure.in.http.create;


import lombok.Data;

@Data
public class AdvertisementCreatePostRequest {

    private String title;

    private String description;

    private String category;

    private String condition;

    private Double price;
}
