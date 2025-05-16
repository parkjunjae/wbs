package com.wbs.wbs.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.wbs.wbs.entity.MineEntity;
import com.wbs.wbs.repository.MineRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MineService {

    private final MineRepository mineRepository;

    public List<MineEntity> getMine(){
        List<MineEntity> mList = mineRepository.findAll();
        return mList;
    }
    
}
