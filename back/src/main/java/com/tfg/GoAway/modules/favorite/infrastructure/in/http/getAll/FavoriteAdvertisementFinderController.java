package com.tfg.GoAway.modules.favorite.infrastructure.in.http.getAll;

import com.tfg.GoAway.modules.advertisement.application.advertisement.finder.AdvertisementFinderAllResponse;
import com.tfg.GoAway.modules.favorite.application.find_all.FavoriteAdvertisementFinder;
import com.tfg.GoAway.modules.shared.security.SecurityUtils;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/favorites")
public class FavoriteAdvertisementFinderController {

    private final FavoriteAdvertisementFinder favoriteFinder;

    @GetMapping
    public List<AdvertisementFinderAllResponse> getFavoriteAdvertisements() {
        String userEmail = SecurityUtils.getUserEmailFromContext();
        return favoriteFinder.findFavorites(userEmail);
    }
}
