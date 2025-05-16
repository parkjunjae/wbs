package com.wbs.wbs.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.wbs.wbs.entity.ulrasonicLig;
import com.wbs.wbs.repository.ulrasonicLogRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ulrasonicLogService {

    private final ulrasonicLogRepository ulrasonicLogRepository;


    public List<ulrasonicLig> getLidar() {
        List<ulrasonicLig> geList = ulrasonicLogRepository.findGroupedData();
        return geList;
    }
    
}
