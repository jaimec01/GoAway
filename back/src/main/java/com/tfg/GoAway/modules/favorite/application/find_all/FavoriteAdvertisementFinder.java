package com.tfg.GoAway.modules.favorite.application.find_all;

import com.tfg.GoAway.modules.advertisement.application.advertisement.finder.AdvertisementFinderAllResponse;
import com.tfg.GoAway.modules.advertisement.domain.Advertisement;
import com.tfg.GoAway.modules.advertisement.application.advertisement.finder.AdvertisementFinderAllMapper;
import com.tfg.GoAway.modules.favorite.domain.FavoriteAdvertisementRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FavoriteAdvertisementFinder {

    private final FavoriteAdvertisementRepository favoriteRepository;
    private final AdvertisementFinderAllMapper advertisementMapper;

    public List<AdvertisementFinderAllResponse> findFavorites(String userEmail) {
        List<Advertisement> advertisements = favoriteRepository.findFavoritesByUserEmail(userEmail);
        return advertisements.stream()
                .map(advertisementMapper::toResponse)
                .collect(Collectors.toList());
    }
}
