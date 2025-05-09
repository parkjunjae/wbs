package com.wbs.wbs.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.wbs.wbs.entity.DetailEntity;
import com.wbs.wbs.repository.DetailRepository;

import lombok.Data;

@Service
@Data
public class DetailService {

    private final DetailRepository detailRepository;


    public List<DetailEntity> detailEntitys(){
        List<DetailEntity> getDetail = detailRepository.findByDelYn("N");
        return getDetail;
    }


    
}
