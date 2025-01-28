package com.tfg.GoAway.modules.favorite.infraestructure.out.db.sql_server;

import org.springframework.data.jpa.repository.JpaRepository;

public interface SqlServerJpaFavoriteAdvertisementRepository extends JpaRepository<FavoriteAdvertisementEntity, FavoriteAdvertisementId> {

}
