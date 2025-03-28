package com.tfg.GoAway.user.advertisement.application.created;

import com.tfg.GoAway.user.advertisement.domain.Advertisement;
import com.tfg.GoAway.user.advertisement.domain.AdvertisementPhoto;
import com.tfg.GoAway.user.advertisement.domain.AdvertisementRepository;
import com.tfg.GoAway.user.advertisement.infrastructure.out.db.sql_server.AdvertisementPhotoStorageService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AdvertisementCreate {

    private final AdvertisementRepository advertisementRepository;
    private final AdvertisementCreateMapper advertisementCreateMapper;
    private final AdvertisementPhotoStorageService photoStorageService;

    public AdvertisementCreateResponse execute(AdvertisementCreateRecord record) {

        // 1) Validaciones
        validate(record);

        // 2) Mapeamos record -> advertisement (sin fotos)
        Advertisement advertisement = advertisementCreateMapper.toDomainWithoutPhotos(record);

        // 3) Guardar en BD y obtener la entidad con ID
        Advertisement savedAd = advertisementRepository.save(advertisement);
        String adId = savedAd.getId();

        // 4) Guardar las fotos en disco (si hay fotos)
        List<String> storedPaths = new ArrayList<>();
        List<MultipartFile> photosToStore = record.getPhotoUrls();

        if (photosToStore != null && !photosToStore.isEmpty()) {
            // Filtrar archivos vacíos
            List<MultipartFile> validPhotos = photosToStore.stream()
                .filter(photo -> photo != null && !photo.isEmpty())
                .collect(Collectors.toList());

            if (!validPhotos.isEmpty()) {
                storedPaths = photoStorageService.storePhotos(
                    validPhotos,
                    record.getUserEmail(),
                    adId
                );
            }
        }

        // 5) Convertir rutas -> AdvertisementPhoto
        List<AdvertisementPhoto> photoList = storedPaths.stream()
            .filter(path -> path != null && !path.trim().isEmpty()) // Filtrar paths vacíos
            .map(path -> AdvertisementPhoto.builder()
                .id(null)
                .advertisementId(adId)
                .userEmail(record.getUserEmail())
                .photoUrl(path)
                .build())
            .collect(Collectors.toList());

        // 6) Asignar fotos a savedAd y volver a guardar
        savedAd.setPhotos(photoList);
        Advertisement finalAd = advertisementRepository.save(savedAd);

        // 7) Respuesta
        return new AdvertisementCreateResponse(
            finalAd.getId(),
            "Anuncio creado con éxito"
        );
    }

    private void validate(AdvertisementCreateRecord record) {
        if (record.getTitle() == null || record.getTitle().trim().isEmpty()) {
            throw new IllegalArgumentException("El título no puede ser nulo o vacío.");
        }
        if (record.getDescription() == null || record.getDescription().trim().isEmpty()) {
            throw new IllegalArgumentException("La descripción no puede ser nula o vacía.");
        }
        if (record.getCategory() == null || record.getCategory().trim().isEmpty()) {
            throw new IllegalArgumentException("La categoría es obligatoria.");
        }
        if (record.getCondition() == null || record.getCondition().trim().isEmpty()) {
            throw new IllegalArgumentException("La condición es obligatoria.");
        }
        if (record.getPrice() == null || record.getPrice() < 0) {
            throw new IllegalArgumentException("El precio no puede ser nulo ni negativo.");
        }
    }
}