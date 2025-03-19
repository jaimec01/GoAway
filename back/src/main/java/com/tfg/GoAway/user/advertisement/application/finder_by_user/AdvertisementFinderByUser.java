package com.tfg.GoAway.user.advertisement.application.finder_by_user;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.tfg.GoAway.user.advertisement.domain.AdvertisementRepository;
import com.tfg.GoAway.user.advertisement.infrastructure.in.http.find_by_user.AdvertisementFinderByUserGetMapper;
import com.tfg.GoAway.user.advertisement.infrastructure.in.http.find_by_user.AdvertisementFinderByUserGetResponse;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AdvertisementFinderByUser {

    private final AdvertisementRepository advertisementRepository;
    private final AdvertisementFinderByUserGetMapper mapper;

    public List<AdvertisementFinderByUserGetResponse> findByUser(String userEmail) {
        List<AdvertisementFinderByUserGetResponse> advertisements = advertisementRepository
                .findByUserEmailOrderByUpdatedAtDesc(userEmail) 
                .stream()
                .map(mapper::toResponse)
                .collect(Collectors.toList());

        return advertisements;
    }
}