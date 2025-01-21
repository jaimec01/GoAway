package com.tfg.GoAway.modules.advertisement.infrastructure.in.http.advertisement.find_by_user;

import java.util.List;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tfg.GoAway.modules.advertisement.application.advertisement.finder_by_user.AdvertisementFinderByUser;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/advertisements")
public class AdvertisementFinderByUserController {

    private final AdvertisementFinderByUser advertisementFinderByUser;

    @GetMapping("/myAdvertisements")
    public List<AdvertisementFinderByUserGetResponse> getAdvertisementsByUser() {
        // Obtener el usuario autenticado del contexto de seguridad
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        String userEmail;
        if (principal instanceof UserDetails) {
            userEmail = ((UserDetails) principal).getUsername();
        } else {
            throw new IllegalStateException("Usuario no autenticado o token inválido.");
        }

        // Obtener anuncios asociados al usuario autenticado
        List<AdvertisementFinderByUserGetResponse> advertisements = advertisementFinderByUser.findByUser(userEmail);

        // Comprobar si la lista está vacía
        if (advertisements.isEmpty()) {
            throw new IllegalStateException("No se encontraron anuncios para este usuario.");
        }

        return advertisements;
    }
}