package com.wbs.wbs.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.wbs.wbs.entity.WeedingEntity;
import com.wbs.wbs.repository.WeedingRepository;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Service
@Data
@RequiredArgsConstructor
public class WeedingService {

    private final WeedingRepository weedingRepository;
    

    public List<WeedingEntity> weedingEntities() {
        return weedingRepository.findByDelYn("N");
    }
    
}
