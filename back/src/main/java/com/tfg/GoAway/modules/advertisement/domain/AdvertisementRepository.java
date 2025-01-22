package com.tfg.GoAway.modules.advertisement.domain;

import java.util.List;
import java.util.Optional;


public interface AdvertisementRepository {

    Advertisement save(Advertisement advertisement);

    Optional<Advertisement> findById(String id);
    
    List<Advertisement> findAll();

    void delete(Advertisement advertisement);

}
