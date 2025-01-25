package com.tfg.GoAway.modules.advertisement.infrastructure.in.http.advertisement.create;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tfg.GoAway.modules.advertisement.domain.Advertisement;
import com.tfg.GoAway.modules.advertisement.infrastructure.out.db.sql_server.SqlServerAdvertisementRepository;
import com.tfg.GoAway.modules.shared.security.SecurityUtils;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/advertisements")
public class AdvertisementCreatePostController {
 
    private final SqlServerAdvertisementRepository advertisementRepository;
    private final AdvertisementCreatePostMapper requestMapper;

    @PostMapping("/create")
    public AdvertisementCreatePostResponse saveAdvertisement(@RequestBody AdvertisementCreatePostRequest request) {

        if (request.getDescription() == null || request.getDescription().isEmpty()) {
            throw new IllegalArgumentException("La descripción no puede ser nula o vacía.");
        }

        if (request.getCategory() == null || request.getCategory().isEmpty()) {
            throw new IllegalArgumentException("La categoría no puede ser nula o vacía.");
        }

        String userEmail = SecurityUtils.getUserEmailFromContext();

        Advertisement advertisement = requestMapper.toDomain(request, userEmail);
        Advertisement savedAdvertisement = advertisementRepository.save(advertisement);

        return new AdvertisementCreatePostResponse(
            savedAdvertisement.getId(),
            "Anuncio creado con éxito"
        );
    }
}
