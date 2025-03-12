package com.tfg.GoAway.user.favorite.infrastructure.in.http.getAll;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import com.tfg.GoAway.user.advertisement.application.advertisement.finder.AdvertisementFinderAllResponse;
import com.tfg.GoAway.user.favorite.application.find_all.FavoriteAdvertisementFinder;
import com.tfg.GoAway.user.shared.security.SecurityUtils;

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
