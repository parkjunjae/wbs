package com.wbs.wbs.repository;

import com.wbs.wbs.entity.RobotEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RobotRepository extends JpaRepository<RobotEntity, Long> {
    Optional<RobotEntity> findByMac(String mac);
}
