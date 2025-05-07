package com.wbs.wbs.controller;

import com.wbs.wbs.dto.TotalDto;
import com.wbs.wbs.entity.TotalEntity;
import com.wbs.wbs.service.TotalService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class TotalController {
    private final TotalService totalService;


    @GetMapping("/data")
    public ResponseEntity<List<TotalDto>> totalEntities(){
        List<TotalEntity> getTotal = totalService.totalEntities();
        if (getTotal.isEmpty()){
            return ResponseEntity.status(404).body(Collections.EMPTY_LIST);
        }
        List<TotalDto> totalDTO = getTotal.stream().map(TotalDto::new).collect(Collectors.toList());
        return ResponseEntity.ok(totalDTO);
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
