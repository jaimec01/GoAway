package com.tfg.GoAway.modules.advertisement.infrastructure.out.db.sql_server;

import org.springframework.data.jpa.repository.JpaRepository;



public interface SqlServerJpaAdvertisementRepository extends JpaRepository<AdvertisementEntity, String> {
}
