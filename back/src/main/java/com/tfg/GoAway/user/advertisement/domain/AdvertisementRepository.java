package com.tfg.GoAway.user.advertisement.domain;

import java.util.List;
import java.util.Optional;

public interface AdvertisementRepository {

    Advertisement save(Advertisement advertisement);

    Optional<Advertisement> findById(String id);
    
    List<Advertisement> findAll();

    void delete(Advertisement advertisement);

    List<Advertisement> findByFilters(String category, String condition);

    Optional<Advertisement> findByIdAndUserEmail(String id, String userEmail);

    List<Advertisement> findByFiltersAndExcludeUser(String email, String category, String condition);

    List<Advertisement> findByFiltersOrderByUpdatedAtDesc(String category, String condition);

    List<Advertisement> findByFiltersOrderByUpdatedAtAsc(String category, String condition);

    List<Advertisement> findByFiltersAndExcludeUserOrderByUpdatedAtDesc(String userEmail, String category, String condition);

    List<Advertisement> findByFiltersAndExcludeUserOrderByUpdatedAtAsc(String userEmail, String category, String condition);

    List<Advertisement> findByUserEmailOrderByUpdatedAtDesc(String userEmail);

}