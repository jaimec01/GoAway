package com.tfg.GoAway.user.advertisement.application.find_by_id;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tfg.GoAway.user.advertisement.domain.AdvertisementRepository;

import java.util.Optional;

@Service
public class AdvertisementFinderById {

    @Autowired
    private AdvertisementRepository advertisementRepository;

    @Autowired
    private AdvertisementFinderByIdMapper advertisementMapper;

    public Optional<AdvertisementFinderByIdResponse> findById(String id) {
        return advertisementRepository.findById(id)
                .map(advertisementMapper::toResponse);
    }
}
