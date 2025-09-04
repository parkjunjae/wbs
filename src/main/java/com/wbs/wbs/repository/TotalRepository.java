package com.wbs.wbs.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.wbs.wbs.entity.TotalEntity;

@Repository
public interface TotalRepository extends JpaRepository<TotalEntity, Long> {

    List<TotalEntity> findByDelYn(String delYn);
    Optional<TotalEntity> findById(Long id);
    
    Optional<TotalEntity> findByMac(String mac);

    Optional<TotalEntity> findByMacIgnoreCase(String mac);

    List<TotalEntity> findByFunc(String func);

}
