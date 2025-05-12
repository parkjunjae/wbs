package com.wbs.wbs.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.wbs.wbs.entity.DetailEntity;

public interface DetailRepository extends JpaRepository<DetailEntity, Long> {

    List<DetailEntity> findByDelYnOrderByTotalEntity_BatteryDesc(String delYn);




    
}
