package com.tfg.GoAway.modules.advertisement.infrastructure.in.http.advertisement.update;

import com.tfg.GoAway.modules.advertisement.application.advertisement.update.AdvertisementUpdate;
import com.tfg.GoAway.modules.advertisement.application.advertisement.update.AdvertisementUpdateRecord;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/advertisements")
public class AdvertisementUpdatePutController {

    private final AdvertisementUpdate advertisementUpdater;

    @PutMapping("/myAdvertisements")
    public ResponseEntity<String> updateAdvertisement(@RequestBody AdvertisementUpdateRequest request) {
        // Obtener el email del usuario autenticado
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String userEmail;

        if (principal instanceof UserDetails) {
            userEmail = ((UserDetails) principal).getUsername();
        } else {
            throw new IllegalStateException("Usuario no autenticado o token inválido.");
        }

        // Crear el record con los datos enviados en el request
        AdvertisementUpdateRecord record = new AdvertisementUpdateRecord(
                request.getId(),
                request.getTitle(),
                request.getDescription(),
                request.getCategory(),
                request.getPhotoUrls(),
                request.getCondition(),
                request.getPrice()
        );

        // Ejecutar la actualización
        advertisementUpdater.updateByUserAndId(userEmail, request.getId(), record);

        return ResponseEntity.ok("Anuncio actualizado correctamente.");
    }
}
