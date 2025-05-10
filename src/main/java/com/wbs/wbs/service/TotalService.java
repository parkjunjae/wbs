package com.wbs.wbs.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.wbs.wbs.entity.TotalEntity;
import com.wbs.wbs.repository.TotalRepository;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Service
@Data
@RequiredArgsConstructor
public class TotalService {

    private final TotalRepository totalRepository;

    public List<TotalEntity> totalEntities(){
        List<TotalEntity> list = totalRepository.findAllByOrderByBatteryDesc();
        return list;
    }

    public Optional<TotalEntity> findById(Long id){
        Optional<TotalEntity> totalEntity = totalRepository.findById(id);
        if (totalEntity.isEmpty()) {
            return null;
        }
        return totalEntity;
    }

}
