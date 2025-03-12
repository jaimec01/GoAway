package com.tfg.GoAway.user.advertisement.application.advertisement.finder;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tfg.GoAway.user.advertisement.domain.AdvertisementRepository;
import com.tfg.GoAway.user.favorite.application.find_all.FavoriteAdvertisementFinder;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class AdvertisementFinderAll {

    @Autowired
    private AdvertisementRepository advertisementRepository;

    @Autowired
    private AdvertisementFinderAllMapper advertisementMapper;

    @Autowired
    private FavoriteAdvertisementFinder favoriteAdvertisement;

    public List<AdvertisementFinderAllResponse> finderAll(String userEmail, String category, String condition, String sortOrder) {
        List<AdvertisementFinderAllResponse> advertisements;
    
        if (userEmail == null || userEmail.isEmpty()) {
            // Usuario no registrado
            if ("asc".equalsIgnoreCase(sortOrder)) {
                advertisements = advertisementRepository.findByFiltersOrderByUpdatedAtAsc(category, condition)
                        .stream()
                        .map(advertisementMapper::toResponse)
                        .collect(Collectors.toList());
            } else {
                // Por defecto, orden descendente
                advertisements = advertisementRepository.findByFiltersOrderByUpdatedAtDesc(category, condition)
                        .stream()
                        .map(advertisementMapper::toResponse)
                        .collect(Collectors.toList());
            }
        } else {
            // Usuario registrado
            if ("asc".equalsIgnoreCase(sortOrder)) {
                advertisements = advertisementRepository.findByFiltersAndExcludeUserOrderByUpdatedAtAsc(userEmail, category, condition)
                        .stream()
                        .map(advertisementMapper::toResponse)
                        .collect(Collectors.toList());
            } else {
                // Por defecto, orden descendente
                advertisements = advertisementRepository.findByFiltersAndExcludeUserOrderByUpdatedAtDesc(userEmail, category, condition)
                        .stream()
                        .map(advertisementMapper::toResponse)
                        .collect(Collectors.toList());
            }
    
            // Marcar anuncios favoritos
            List<AdvertisementFinderAllResponse> favoriteAdvertisements = favoriteAdvertisement.findFavorites(userEmail);
            Set<String> favoriteIds = favoriteAdvertisements.stream()
                    .map(AdvertisementFinderAllResponse::getId)
                    .collect(Collectors.toSet());
    
            advertisements.forEach(ad -> ad.setIsFavorite(favoriteIds.contains(ad.getId())));
        }
    
        return advertisements;
    }
}