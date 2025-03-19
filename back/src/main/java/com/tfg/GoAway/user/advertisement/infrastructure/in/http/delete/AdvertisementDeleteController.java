package com.tfg.GoAway.user.advertisement.infrastructure.in.http.delete;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tfg.GoAway.shared.security.SecurityUtils;
import com.tfg.GoAway.user.advertisement.application.delete.AdvertisementDelete;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/advertisements/myAdvertisements")
public class AdvertisementDeleteController {

    private final AdvertisementDelete advertisementDeleter;

    @DeleteMapping
    public ResponseEntity<String> deleteAdvertisement(@RequestBody AdvertisementDeleteRequest request) {
        String userEmail = SecurityUtils.getUserEmailFromContext();
        
        try {

            advertisementDeleter.deleteByUserAndId(userEmail, request.getAdvertisementId());
            return ResponseEntity.ok("Anuncio eliminado correctamente.");

        } catch (DataIntegrityViolationException e) {

            return ResponseEntity.badRequest().body("Este anuncio tiene una transacción abierta y no puede eliminarse.");

        } catch (Exception e) {

            return ResponseEntity.status(500).body("Error inesperado al intentar eliminar el anuncio.");
            
        }
    }
}
