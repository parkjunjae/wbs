package com.wbs.wbs.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.wbs.wbs.entity.MineEntity;
import com.wbs.wbs.entity.TotalEntity;


@Repository
public interface MineRepository extends JpaRepository<MineEntity, Long> {

    Optional<MineEntity> findFirstByTotalEntityOrderByTimeDesc(TotalEntity totalEntity);

    Optional<MineEntity> findByMineId(Long mineId);


}
