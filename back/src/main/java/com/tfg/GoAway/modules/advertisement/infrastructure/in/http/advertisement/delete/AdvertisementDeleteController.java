package com.tfg.GoAway.modules.advertisement.infrastructure.in.http.advertisement.delete;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tfg.GoAway.modules.advertisement.application.advertisement.delete.AdvertisementDelete;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/advertisements")
public class AdvertisementDeleteController {

    private final AdvertisementDelete advertisementDeleter;

    @DeleteMapping("/myAdvertisements")
    public ResponseEntity<String> deleteAdvertisement(@RequestBody AdvertisementDeleteRequest request) {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String userEmail;
        
        if (principal instanceof UserDetails) {
            userEmail = ((UserDetails) principal).getUsername();
        } else {
            throw new IllegalStateException("Usuario no autenticado o token inválido.");
        }
        
        advertisementDeleter.deleteByUserAndId(userEmail, request.getAdvertisementId());

        return ResponseEntity.ok("Anuncio eliminado correctamente.");
    }
}
