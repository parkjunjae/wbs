package com.wbs.wbs.repository;

import com.wbs.wbs.entity.AudioEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AudioRepository extends JpaRepository<AudioEntity, Long> {
}
