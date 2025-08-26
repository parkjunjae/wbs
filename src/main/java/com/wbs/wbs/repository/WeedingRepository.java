package com.wbs.wbs.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.wbs.wbs.entity.WeedingEntity;

@Repository
public interface WeedingRepository extends JpaRepository<WeedingEntity, Long> {

    List<WeedingEntity> findByDelYn(String delYn);

    Optional<WeedingEntity> findByMac(String mac);
    
}
