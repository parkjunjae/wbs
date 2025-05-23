package com.wbs.wbs.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.wbs.wbs.entity.DetailEntity;
import com.wbs.wbs.entity.MineEntity;

@Repository
public interface MineRepository extends JpaRepository<MineEntity, Long> {

    Optional<MineEntity> findFirstByDetailEntityOrderByTimeDesc(DetailEntity detailEntity);

}
