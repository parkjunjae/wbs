package com.wbs.wbs.repository;

import com.wbs.wbs.entity.TotalEntity;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TotalRepository extends JpaRepository<TotalEntity, Long> {

    List<TotalEntity> findAllByOrderByBatteryDesc();
    Optional<TotalEntity> findById(Long id);
}
