package com.wbs.wbs.repository;


import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.wbs.wbs.entity.MusterEntity;

@Repository
public interface MusterRepository extends JpaRepository<MusterEntity, Long> {

    
    Optional<MusterEntity> findTopByOrderByCreatedAtDesc();

}
