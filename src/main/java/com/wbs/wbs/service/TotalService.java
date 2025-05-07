package com.wbs.wbs.service;

import com.wbs.wbs.entity.TotalEntity;
import com.wbs.wbs.repository.TotalRepository;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Data
@RequiredArgsConstructor
public class TotalService {

    private final TotalRepository totalRepository;

    public List<TotalEntity> totalEntities(){
        List<TotalEntity> list = totalRepository.findAll();
        if (list.isEmpty()){
            return null;
        }
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
