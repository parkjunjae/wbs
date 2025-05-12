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
        List<TotalEntity> list = totalRepository.findByDelYnOrderByBatteryDesc("N");
        return list;
    }

    

    public Optional<TotalEntity> updateData(Long id){
        Optional<TotalEntity> findOptional = totalRepository.findById(id);
        if (findOptional.isEmpty()) {
            return Optional.empty();
        }
        
        TotalEntity tEntity = findOptional.get();
        tEntity.setDelYn("Y");
        totalRepository.save(tEntity);

        return Optional.of(tEntity);
    }

}
