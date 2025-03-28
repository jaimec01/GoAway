package com.tfg.GoAway.user.favorite.infrastructure.in.http.post;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tfg.GoAway.shared.security.SecurityUtils;
import com.tfg.GoAway.user.favorite.domain.FavoriteAdvertisement;
import com.tfg.GoAway.user.favorite.infrastructure.out.db.sql_server.SqlServerFavoriteAdvertisementRepository;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/favorites")
public class FavoriteAdvertisementCreatePostController {

    private final SqlServerFavoriteAdvertisementRepository favoriteRepository;
    private final FavoriteAdvertisementCreatePostMapper requestMapper;

    @PostMapping
    public FavoriteAdvertisementCreatePostResponse toggleFavorite(@RequestBody FavoriteAdvertisementCreatePostRequest request) {

        if (request.getAdvertisementId() == null || request.getAdvertisementId().isEmpty()) {
            throw new IllegalArgumentException("El ID del anuncio no puede ser nulo o vacío.");
        }

        // Obtener el email del usuario autenticado desde el contexto de seguridad
        String userEmail = SecurityUtils.getUserEmailFromContext();

        // Verificar si ya existe el favorito
        boolean exists = favoriteRepository.existsById(request.getAdvertisementId());

        if (exists) {
            // Si ya existe, eliminarlo
            favoriteRepository.delete(request.getAdvertisementId());
            return new FavoriteAdvertisementCreatePostResponse(
                userEmail,
                request.getAdvertisementId(),
                "Favorito eliminado con éxito"
            );
        } else {
            // Si no existe, agregarlo
            FavoriteAdvertisement favorite = requestMapper.toDomain(request, userEmail);
            favoriteRepository.save(favorite);
            return new FavoriteAdvertisementCreatePostResponse(
                userEmail,
                request.getAdvertisementId(),
                "Favorito añadido con éxito"
            );
        }
    }
}
