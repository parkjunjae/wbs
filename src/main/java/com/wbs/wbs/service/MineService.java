package com.wbs.wbs.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.wbs.wbs.entity.MineEntity;
import com.wbs.wbs.entity.TotalEntity;
import com.wbs.wbs.repository.DetailRepository;
import com.wbs.wbs.repository.MineRepository;
import com.wbs.wbs.repository.TotalRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MineService {

    private final MineRepository mineRepository;
    private final DetailRepository detailRepository;
    private final TotalRepository totalRepository;

    public List<MineEntity> getMine(){
        List<MineEntity> mList = mineRepository.findAll();
        return mList;
    }

    public Optional<MineEntity> getAudio(String mac){
        Optional<TotalEntity> totalOpt = totalRepository.findByMacIgnoreCase(mac);
        System.out.println("🔍 totalEntity: " + totalOpt);
        if(totalOpt.isEmpty()) {
            return Optional.empty();
        }
        TotalEntity totalEntity = totalOpt.get(); 
        return mineRepository.findFirstByTotalEntityOrderByTimeDesc(totalEntity);
    }

    
}
