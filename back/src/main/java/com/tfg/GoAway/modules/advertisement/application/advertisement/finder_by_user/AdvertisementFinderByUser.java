package com.tfg.GoAway.modules.advertisement.application.advertisement.finder_by_user;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.tfg.GoAway.modules.advertisement.domain.AdvertisementRepository;
import com.tfg.GoAway.modules.advertisement.infrastructure.in.http.advertisement.find_by_user.AdvertisementFinderByUserGetMapper;
import com.tfg.GoAway.modules.advertisement.infrastructure.in.http.advertisement.find_by_user.AdvertisementFinderByUserGetResponse;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AdvertisementFinderByUser {

    private final AdvertisementRepository advertisementRepository;
    private final AdvertisementFinderByUserGetMapper mapper;

    public List<AdvertisementFinderByUserGetResponse> findByUser(String userEmail) {
        
        return advertisementRepository.findAll().stream()
                .filter(advertisement -> userEmail.equals(advertisement.getUserEmail()))
                .map(mapper::toResponse)
                .collect(Collectors.toList());
    }
}