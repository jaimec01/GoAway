package com.tfg.GoAway.modules.advertisement.application.advertisement.finder;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tfg.GoAway.modules.advertisement.domain.AdvertisementRepository;
import com.tfg.GoAway.modules.favorite.application.find_all.FavoriteAdvertisementFinder;

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

    public List<AdvertisementFinderAllResponse> finderAll(String userEmail) {
        List<AdvertisementFinderAllResponse> advertisements;

        // 1️⃣ Si el usuario NO está autenticado, devolver TODOS los anuncios
        if (userEmail == null || userEmail.isEmpty()) {
            advertisements = advertisementRepository.findAll()
                    .stream()
                    .map(advertisementMapper::toResponse)
                    .collect(Collectors.toList());
        } else {
            // 2️⃣ Si el usuario está autenticado, devolver solo los anuncios que NO ha creado él mismo
            advertisements = advertisementRepository.findAllExcludingUser(userEmail)
                    .stream()
                    .map(advertisementMapper::toResponse)
                    .collect(Collectors.toList());

            // 3️⃣ Obtener la lista de favoritos del usuario
            List<AdvertisementFinderAllResponse> favoriteAdvertisements = favoriteAdvertisement.findFavorites(userEmail);

            // 4️⃣ Convertir la lista de favoritos en un Set para búsqueda rápida
            Set<String> favoriteIds = favoriteAdvertisements.stream()
                    .map(AdvertisementFinderAllResponse::getId)
                    .collect(Collectors.toSet());

            // 5️⃣ Marcar los anuncios como favoritos si están en la lista
            advertisements.forEach(ad -> ad.setIsFavorite(favoriteIds.contains(ad.getId())));
        }

        return advertisements;
    }
}
