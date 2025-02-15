package com.tfg.GoAway.modules.advertisement.application.advertisement.find_by_id;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tfg.GoAway.modules.advertisement.domain.AdvertisementRepository;

import java.util.Optional;

@Service
public class AdvertisementFinderById {

    @Autowired
    private AdvertisementRepository advertisementRepository;

    @Autowired
    private AdvertisementFinderByIdMapper advertisementMapper;

    public Optional<AdvertisementFinderByIdResponse> findByUserAndId(String userEmail, String id) {
        return advertisementRepository.findByIdAndUserEmail(id, userEmail)
                .map(advertisementMapper::toResponse);
    }
}
