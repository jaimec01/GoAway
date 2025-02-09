package com.tfg.GoAway.modules.advertisement.application.advertisement.filter_by_category;

import com.tfg.GoAway.modules.advertisement.domain.Advertisement;
import com.tfg.GoAway.modules.advertisement.domain.AdvertisementRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AdvertisementGetByFilters {

    private final AdvertisementRepository advertisementRepository;

    public List<AdvertisementGetByFiltersResponse> execute(String category, String condition) {
        List<Advertisement> advertisements = advertisementRepository.findByFilters(category, condition);

        if (advertisements.isEmpty()) {
            throw new IllegalArgumentException("No hay muebles con los filtros seleccionados.");
        }

        return advertisements.stream()
                .map(AdvertisementGetByFiltersMapper::toResponse)
                .collect(Collectors.toList());
    }
}