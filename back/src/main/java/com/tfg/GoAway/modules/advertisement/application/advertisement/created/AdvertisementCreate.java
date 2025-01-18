package com.tfg.GoAway.modules.advertisement.application.advertisement.created;

import com.tfg.GoAway.modules.advertisement.domain.Advertisement;
import com.tfg.GoAway.modules.advertisement.domain.AdvertisementRepository;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AdvertisementCreate {

    private final AdvertisementRepository advertisementRepository;

    public AdvertisementCreateResponse execute(AdvertisementCreateRecord record) {
        // Mapear el record al dominio
        Advertisement advertisement = AdvertisementCreateMapper.toDomain(record);

        // Guardar el anuncio
        Advertisement savedAdvertisement = advertisementRepository.save(advertisement);

        // Retornar la respuesta
        return new AdvertisementCreateResponse(
                savedAdvertisement.getId(),
                "Anuncio creado con éxito"
        );
    }
}

