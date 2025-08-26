package com.wbs.wbs.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.wbs.wbs.entity.WeedingEntity;
import com.wbs.wbs.service.WeedingService;

import lombok.RequiredArgsConstructor;


@RestController
@RequiredArgsConstructor
public class WeedingController {

    private final WeedingService weedingService;


    @GetMapping("/weed/data")
    public ResponseEntity<List<WeedingEntity>> getWeeding() {
        List<WeedingEntity> weeding = weedingService.weedingEntities();
        if (weeding == null || weeding.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(weeding);
    }
    
    
}
