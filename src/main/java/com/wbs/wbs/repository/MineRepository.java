package com.wbs.wbs.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.wbs.wbs.entity.MineEntity;

@Repository
public interface MineRepository extends JpaRepository<MineEntity, Long> {

    

}
