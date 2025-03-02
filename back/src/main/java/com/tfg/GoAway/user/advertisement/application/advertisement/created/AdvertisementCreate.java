package com.tfg.GoAway.user.advertisement.application.advertisement.created;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import com.tfg.GoAway.user.advertisement.domain.Advertisement;
import com.tfg.GoAway.user.advertisement.domain.AdvertisementRepository;

@Service
@RequiredArgsConstructor
public class AdvertisementCreate {

    private final AdvertisementRepository advertisementRepository;

    public AdvertisementCreateResponse execute(AdvertisementCreateRecord record) {

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
    
        if (record.getPhotoUrls() == null || record.getPhotoUrls().trim().isEmpty()) {
            throw new IllegalArgumentException("Debe proporcionar al menos una URL de foto.");
        }
        Advertisement advertisement = AdvertisementCreateMapper.toDomain(record);

        Advertisement savedAdvertisement = advertisementRepository.save(advertisement);

        return new AdvertisementCreateResponse(
                savedAdvertisement.getId(),
                "Anuncio creado con éxito"
        );
    }
}
