package com.tfg.GoAway.modules.advertisement.application.advertisement.update;

import com.tfg.GoAway.modules.advertisement.domain.Advertisement;
import com.tfg.GoAway.modules.advertisement.domain.AdvertisementRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class AdvertisementUpdate {

    private final AdvertisementRepository advertisementRepository;

    public void updateByUserAndId(String userEmail, String advertisementId, AdvertisementUpdateRecord record) {

        Advertisement existingAdvertisement = advertisementRepository.findById(advertisementId)
                .filter(ad -> userEmail.equals(ad.getUserEmail()))
                .orElseThrow(() -> new IllegalStateException("El anuncio no existe o no pertenece al usuario."));

        Advertisement updatedAdvertisement = AdvertisementUpdateMapper.mergeChanges(existingAdvertisement, record);

        advertisementRepository.save(updatedAdvertisement);
    }
}
