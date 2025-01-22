package com.tfg.GoAway.modules.advertisement.application.advertisement.delete;

import org.springframework.stereotype.Service;

import com.tfg.GoAway.modules.advertisement.domain.AdvertisementRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AdvertisementDeleter {

    private final AdvertisementRepository advertisementRepository;

    public void deleteByUserAndId(String userEmail, String advertisementId) {
        advertisementRepository.findById(advertisementId)
            .filter(ad -> userEmail.equals(ad.getUserEmail()))
            .ifPresentOrElse(
                advertisementRepository::delete,
                () -> {
                    throw new IllegalStateException("Anuncio no encontrado o no pertenece al usuario.");
                }
            );
    }
}
