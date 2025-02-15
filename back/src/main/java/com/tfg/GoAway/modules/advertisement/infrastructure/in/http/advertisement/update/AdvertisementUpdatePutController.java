package com.tfg.GoAway.modules.advertisement.infrastructure.in.http.advertisement.update;

import com.tfg.GoAway.modules.advertisement.application.advertisement.update.AdvertisementUpdate;
import com.tfg.GoAway.modules.advertisement.application.advertisement.update.AdvertisementUpdateRecord;
import com.tfg.GoAway.modules.shared.security.SecurityUtils;

import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/advertisements")
public class AdvertisementUpdatePutController {

    private final AdvertisementUpdate advertisementUpdater;

    @PutMapping("/{id}")
    public ResponseEntity<?> updateAdvertisement(@PathVariable String id, @RequestBody AdvertisementUpdateRequest request) {

        String userEmail = SecurityUtils.getUserEmailFromContext();

        AdvertisementUpdateRecord record = new AdvertisementUpdateRecord(
                id,
                request.getTitle(),
                request.getDescription(),
                request.getCategory(),
                request.getPhotoUrls(),
                request.getCondition(),
                request.getPrice());

        // Ejecutar la actualización
        advertisementUpdater.updateByUserAndId(userEmail, request.getId(), record);

        return ResponseEntity.ok(Map.of("message", "Anuncio actualizado correctamente."));
    }
}
