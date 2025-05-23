package com.wbs.wbs.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.wbs.wbs.entity.DetailEntity;
import com.wbs.wbs.entity.MineEntity;
import com.wbs.wbs.repository.DetailRepository;
import com.wbs.wbs.repository.MineRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MineService {

    private final MineRepository mineRepository;
    private final DetailRepository detailRepository;

    public List<MineEntity> getMine(){
        List<MineEntity> mList = mineRepository.findAll();
        return mList;
    }

    public Optional<MineEntity> getAudio(Long detailId){
        Optional<DetailEntity> detailEntityOpt = detailRepository.findById(detailId);
        if(detailEntityOpt.isEmpty()) {
            return null;
        }
        DetailEntity detailEntity = detailEntityOpt.get(); 
        return mineRepository.findFirstByDetailEntityOrderByTimeDesc(detailEntity);
    }

    
}
