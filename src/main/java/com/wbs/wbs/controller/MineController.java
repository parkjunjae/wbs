package com.wbs.wbs.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import com.wbs.wbs.entity.MineEntity;
import com.wbs.wbs.service.MineService;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


@RestController
@RequiredArgsConstructor
public class MineController {

    private final MineService mineService;


    @GetMapping("/mine")
    public ResponseEntity<List<MineEntity>> getMine(){
        List<MineEntity> getList = mineService.getMine();
        if(getList.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(getList);
    }
    
}
