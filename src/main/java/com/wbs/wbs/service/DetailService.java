package com.wbs.wbs.service;

import org.springframework.stereotype.Service;

import com.wbs.wbs.repository.DetailRepository;

import lombok.Data;

@Service
@Data
public class DetailService {

    private final DetailRepository detailRepository;


    // public List<DetailEntity> detailEntitys(){
    //     List<DetailEntity> getDetail = detailRepository.findByDelYnOrderByTotalEntity_BatteryDesc("N");
    //     return getDetail;
    // }


    
}
