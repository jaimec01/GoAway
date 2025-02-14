package com.tfg.GoAway.modules.advertisement.infrastructure.in.http.advertisement.delete;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tfg.GoAway.modules.advertisement.application.advertisement.delete.AdvertisementDelete;
import com.tfg.GoAway.modules.shared.security.SecurityUtils;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/advertisements/myAdvertisements")
public class AdvertisementDeleteController {

    private final AdvertisementDelete advertisementDeleter;

    @DeleteMapping
    public ResponseEntity<String> deleteAdvertisement(@RequestBody AdvertisementDeleteRequest request) {
        
        String userEmail = SecurityUtils.getUserEmailFromContext();
        
        advertisementDeleter.deleteByUserAndId(userEmail, request.getAdvertisementId());

        return ResponseEntity.ok("Anuncio eliminado correctamente.");
    }
}
