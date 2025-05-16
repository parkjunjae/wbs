package com.wbs.wbs.controller;

import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.wbs.wbs.service.ulrasonicLogService;

import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.wbs.wbs.entity.ulrasonicLig;



@RestController
@RequiredArgsConstructor
public class UlrasonicLogController {

    private final ulrasonicLogService ulrasonicLogService;


    @GetMapping("/lidar")
    public ResponseEntity<List<ulrasonicLig>> receive() {
        List<ulrasonicLig> getList = ulrasonicLogService.getLidar();
        if(getList.isEmpty()){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(getList);    
    }
    
}
