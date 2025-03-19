package com.tfg.GoAway.user.advertisement.application.update;

import java.util.List;
import java.util.stream.Collectors;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import com.tfg.GoAway.user.advertisement.domain.Advertisement;
import com.tfg.GoAway.user.advertisement.domain.AdvertisementPhoto;
import com.tfg.GoAway.user.advertisement.domain.AdvertisementRepository;
import com.tfg.GoAway.user.advertisement.infrastructure.out.db.sql_server.AdvertisementPhotoStorageService;

@Service
@RequiredArgsConstructor
public class AdvertisementUpdate {

    private final AdvertisementRepository advertisementRepository;
    private final AdvertisementPhotoStorageService photoStorageService;

    public void updateByUserAndId(String userEmail, String advertisementId, AdvertisementUpdateRecord record) {
        // 1) Validar que el anuncio existe
        Advertisement existingAdvertisement = advertisementRepository.findById(advertisementId)
                .orElseThrow(() -> new IllegalStateException("El anuncio no existe."));

        // 2) Validar que el usuario es el propietario del anuncio
        if (!existingAdvertisement.getUserEmail().equalsIgnoreCase(userEmail)) {
            throw new IllegalStateException("No tienes permiso para modificar este anuncio.");
        }

        // 3) Validar los datos del registro
        validate(record);

        // 4) Filtrar las imágenes existentes que se deben conservar
        List<AdvertisementPhoto> photosToKeep = existingAdvertisement.getPhotos().stream()
                .filter(photo -> record.getExistingPhotoIds() != null && record.getExistingPhotoIds().contains(photo.getId()))
                .collect(Collectors.toList());
        System.out.println("Fotos a conservar (photosToKeep): " + photosToKeep);

        // 5) Guardar las nuevas fotos en disco (si se proporcionan)
        List<AdvertisementPhoto> newPhotos = List.of();
        if (record.getPhotoUrls() != null && !record.getPhotoUrls().isEmpty()) {
            List<String> storedPaths = photoStorageService.storePhotos(
                    record.getPhotoUrls(),
                    userEmail,
                    advertisementId
            );
            System.out.println("Rutas de nuevas fotos (storedPaths): " + storedPaths);
            newPhotos = storedPaths.stream()
                    .map(path -> AdvertisementPhoto.builder()
                            .id(null) // Se generará automáticamente
                            .advertisementId(advertisementId)
                            .userEmail(userEmail)
                            .photoUrl(path)
                            .build())
                    .collect(Collectors.toList());
        }

        // 6) Validar el total de imágenes
        int totalPhotos = photosToKeep.size() + newPhotos.size();
        if (totalPhotos > 6) {
            throw new IllegalStateException("No puedes tener más de 6 imágenes en total.");
        }

        // 7) Combinar las imágenes existentes (conservadas) con las nuevas
        photosToKeep.addAll(newPhotos);
        System.out.println("Fotos combinadas (photosToKeep final): " + photosToKeep);

        // 8) Mapear los cambios al anuncio existente usando el mapper
        Advertisement updatedAdvertisement = AdvertisementUpdateMapper.mergeChanges(existingAdvertisement, record, photosToKeep);

        // 9) Guardar el anuncio actualizado
        advertisementRepository.save(updatedAdvertisement);
    }

    private void validate(AdvertisementUpdateRecord record) {
        if (record.getTitle() != null && record.getTitle().trim().isEmpty()) {
            throw new IllegalArgumentException("El título no puede estar vacío.");
        }
        if (record.getDescription() != null && record.getDescription().trim().isEmpty()) {
            throw new IllegalArgumentException("La descripción no puede estar vacía.");
        }
        if (record.getPrice() != null && record.getPrice() < 0) {
            throw new IllegalArgumentException("El precio no puede ser negativo.");
        }
    }
}