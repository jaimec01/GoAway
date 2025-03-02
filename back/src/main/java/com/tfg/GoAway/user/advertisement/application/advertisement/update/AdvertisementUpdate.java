package com.tfg.GoAway.user.advertisement.application.advertisement.update;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import com.tfg.GoAway.user.advertisement.domain.Advertisement;
import com.tfg.GoAway.user.advertisement.domain.AdvertisementRepository;

@Service
@RequiredArgsConstructor
@Slf4j
public class AdvertisementUpdate {

    private final AdvertisementRepository advertisementRepository;

    public void updateByUserAndId(String userEmail, String advertisementId, AdvertisementUpdateRecord record) {

        Advertisement existingAdvertisement = advertisementRepository.findById(advertisementId)
                .orElseThrow(() -> new IllegalStateException("El anuncio no existe."));

        if (!existingAdvertisement.getUserEmail().equalsIgnoreCase(userEmail)) {
            throw new IllegalStateException("No tienes permiso para modificar este anuncio.");
        }

        Advertisement updatedAdvertisement = AdvertisementUpdateMapper.mergeChanges(existingAdvertisement, record);

        advertisementRepository.save(updatedAdvertisement);
    }
}