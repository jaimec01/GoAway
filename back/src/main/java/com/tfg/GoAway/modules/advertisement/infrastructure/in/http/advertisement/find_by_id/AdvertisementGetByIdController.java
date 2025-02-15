package com.tfg.GoAway.modules.advertisement.infrastructure.in.http.advertisement.find_by_id;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tfg.GoAway.modules.advertisement.application.advertisement.find_by_id.AdvertisementFinderById;
import com.tfg.GoAway.modules.advertisement.application.advertisement.find_by_id.AdvertisementFinderByIdResponse;
import com.tfg.GoAway.modules.shared.security.SecurityUtils;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/advertisements")
public class AdvertisementGetByIdController {

    private final AdvertisementFinderById advertisementFinderById;

    @GetMapping("/myAdvertisement/{id}")
    public ResponseEntity<AdvertisementFinderByIdResponse> getMyAdvertisementById(@PathVariable String id) {
        // Obtener el email del usuario autenticado
        String userEmail = SecurityUtils.getUserEmailFromContext();

        return advertisementFinderById.findByUserAndId(userEmail, id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

}
