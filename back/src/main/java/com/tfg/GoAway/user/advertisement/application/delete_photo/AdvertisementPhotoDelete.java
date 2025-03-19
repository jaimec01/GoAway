package com.tfg.GoAway.user.advertisement.application.delete_photo;

import com.tfg.GoAway.user.advertisement.domain.AdvertisementRepository;
import com.tfg.GoAway.shared.security.SecurityUtils;
import com.tfg.GoAway.user.advertisement.domain.AdvertisementPhoto;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AdvertisementPhotoDelete {

    private final AdvertisementRepository advertisementRepository;

    public void deletePhoto(String advertisementId, String photoId) {
        // Obtener el email del usuario autenticado
        String userEmail = SecurityUtils.getUserEmailFromContext();

        // Buscar el anuncio por ID
        var advertisement = advertisementRepository.findById(advertisementId)
                .orElseThrow(() -> new IllegalArgumentException("Anuncio no encontrado."));

        // Verificar que el anuncio pertenece al usuario autenticado
        if (!advertisement.getUserEmail().equals(userEmail)) {
            throw new IllegalStateException("No tienes permisos para modificar este anuncio.");
        }

        // Buscar la foto por ID en la lista de fotos del anuncio
        AdvertisementPhoto photoToRemove = advertisement.getPhotos().stream()
                .filter(photo -> photo.getId().equals(photoId))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Foto no encontrada."));

        // Eliminar la foto de la lista de fotos del anuncio
        advertisement.getPhotos().remove(photoToRemove);

        // Guardar el anuncio actualizado en el repositorio
        advertisementRepository.save(advertisement);
    }
}