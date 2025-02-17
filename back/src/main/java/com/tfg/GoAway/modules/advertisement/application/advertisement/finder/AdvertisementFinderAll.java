package com.tfg.GoAway.modules.advertisement.application.advertisement.finder;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tfg.GoAway.modules.advertisement.domain.AdvertisementRepository;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class AdvertisementFinderAll {

    @Autowired
    private AdvertisementRepository advertisementRepository;

    @Autowired
    private AdvertisementFinderAllMapper advertisementMapper;

    public List<AdvertisementFinderAllResponse> finderAll(String userEmail) {
        List<AdvertisementFinderAllResponse> advertisements;

        if (userEmail == null || userEmail.isEmpty()) {
            advertisements = advertisementRepository.findAll()
                    .stream()
                    .map(advertisementMapper::toResponse)
                    .collect(Collectors.toList());
        } else {
            advertisements = advertisementRepository.findAllExcludingUser(userEmail)
                    .stream()
                    .map(advertisementMapper::toResponse)
                    .collect(Collectors.toList());
        }

        return advertisements;
    }
}
