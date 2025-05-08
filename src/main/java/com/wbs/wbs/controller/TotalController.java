package com.wbs.wbs.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.wbs.wbs.entity.TotalEntity;
import com.wbs.wbs.service.TotalService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class TotalController {
    private final TotalService totalService;


    @GetMapping("/data")
    public ResponseEntity<List<TotalEntity>> totalEntities(){
        List<TotalEntity> getTotal = totalService.totalEntities();
        if (getTotal == null || getTotal.isEmpty()) {
            return ResponseEntity.noContent().build();  
        }
        return ResponseEntity.ok(getTotal);
    }

    @GetMapping("/data/{id}")
    public ResponseEntity<TotalDto> findById(@PathVariable Long id){
        Optional<TotalEntity> totalEntity = totalService.findById(id);
        if (totalEntity.isEmpty()){
            return ResponseEntity.status(404).body(null);
        }
        return ResponseEntity.ok(new TotalDto(totalEntity.get()));
    }
}
