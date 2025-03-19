package com.tfg.GoAway.user.advertisement.application.delete;

import org.springframework.stereotype.Service;

import com.tfg.GoAway.user.advertisement.domain.AdvertisementRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AdvertisementDelete {

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
