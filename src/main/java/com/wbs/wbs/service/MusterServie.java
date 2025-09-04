package com.wbs.wbs.service;

import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import com.wbs.wbs.dto.MusterDto;
import com.wbs.wbs.entity.MusterEntity;
import com.wbs.wbs.entity.MusterEntity.ClickSource;
import com.wbs.wbs.repository.MusterRepository;
import com.wbs.wbs.repository.TotalRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MusterServie {

    private final MusterRepository musterRepo;
    private final TotalRepository totalRepo;


    //좌표 insert
    @Transactional
    public MusterDto.MusterRes create(MusterDto req){
        
        if(req.getLatitude() == null || req.getLongitude() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "latitude/longitude는 필수입니다.");
        }

        MusterEntity m = new MusterEntity();
        m.setLatitude(req.getLatitude());
        m.setLongitude(req.getLongitude());
        m.setCellRow(req.getCellRow());
        m.setCellNum(req.getCellNum());
        m.setQIndex(req.getQIndex());
        m.setSource(parseSource(req.getSource()));
        
        MusterEntity saved = musterRepo.save(m);
        return toResponse(saved);
    }


    //좌표 select
    @Transactional(readOnly = true)
    public Optional<MusterDto.MusterRes> latest(){
        return musterRepo.findTopByOrderByCreatedAtDesc().map(this::toResponse);
    }


    private ClickSource parseSource(String src) {
        if (src == null) return ClickSource.MAP; 
        try {
            return ClickSource.valueOf(src.toUpperCase());
        } catch (IllegalArgumentException ex) {
            throw new ResponseStatusException(
                HttpStatus.BAD_REQUEST, "source는 GRID 또는 MAP 이어야 합니다.");
        }
    }

    private MusterDto.MusterRes toResponse(MusterEntity m) {
        return MusterDto.MusterRes.builder()
            .id(m.getMusterId())
            .latitude(m.getLatitude())
            .longitude(m.getLongitude())
            .cellRow(m.getCellRow())
            .cellNum(m.getCellNum())
            .source(m.getSource() != null ? m.getSource().name() : null)
            .qIndex(m.getQIndex())
            .createdAt(m.getCreatedAt())
            .build();
    }
    
}
