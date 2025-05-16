package com.wbs.wbs.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.wbs.wbs.entity.ulrasonicLig;

@Repository
public interface ulrasonicLogRepository extends JpaRepository<ulrasonicLig, Long> {

    @Query("SELECT a FROM ulrasonicLig a GROUP BY a.id ORDER BY a.timestamp DESC")
    List<ulrasonicLig> findGroupedData();
    
}
