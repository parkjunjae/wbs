package com.wbs.wbs.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.wbs.wbs.dto.MineMediaType;
import com.wbs.wbs.entity.MineMediaEntity;

public interface MineMediaRepository extends JpaRepository<MineMediaEntity, Long> {

Optional<MineMediaEntity> findByMine_MineIdAndMediaType(Long mineId, MineMediaType type);
  boolean existsByMine_MineIdAndMediaType(Long mineId, MineMediaType type);
}
