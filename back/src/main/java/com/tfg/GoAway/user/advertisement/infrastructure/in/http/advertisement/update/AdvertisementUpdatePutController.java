package com.tfg.GoAway.user.advertisement.infrastructure.in.http.advertisement.update;

import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.tfg.GoAway.user.advertisement.application.advertisement.update.AdvertisementUpdate;
import com.tfg.GoAway.user.advertisement.application.advertisement.update.AdvertisementUpdateRecord;
import com.tfg.GoAway.user.shared.security.SecurityUtils;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/advertisements")
public class AdvertisementUpdatePutController {

    private final AdvertisementUpdate advertisementUpdater;

    @PutMapping(value = "/{id}", consumes = "multipart/form-data")
    public ResponseEntity<?> updateAdvertisement(
            @PathVariable String id,
            @RequestPart("advertisement") AdvertisementUpdateRequest request,
            @RequestPart(value = "photos", required = false) List<MultipartFile> photos) {

        String userEmail = SecurityUtils.getUserEmailFromContext();

        AdvertisementUpdateRecord record = new AdvertisementUpdateRecord(
                id,
                request.getTitle(),
                request.getDescription(),
                request.getCategory(),
                photos,
                request.getCondition(),
                request.getPrice(),
                request.isActive(),
                request.getExistingPhotoIds() 
        );

        // Ejecutar la actualización
        advertisementUpdater.updateByUserAndId(userEmail, id, record);

        return ResponseEntity.ok(Map.of("message", "Anuncio actualizado correctamente."));
    }
}