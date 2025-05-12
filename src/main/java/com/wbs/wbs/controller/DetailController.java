package com.wbs.wbs.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.wbs.wbs.entity.DetailEntity;
import com.wbs.wbs.service.DetailService;

import lombok.RequiredArgsConstructor;


@RestController
@RequiredArgsConstructor
public class DetailController {

    private final DetailService detailService;

    @GetMapping("/operation")    
    public ResponseEntity<List<DetailEntity>> getDetail(){
        List<DetailEntity> gEntitys = detailService.detailEntitys();
        if(gEntitys == null || gEntitys.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(gEntitys);
    }
    
}
