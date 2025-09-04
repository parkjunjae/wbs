package com.wbs.wbs.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.wbs.wbs.dto.MusterDto;
import com.wbs.wbs.service.MusterServie;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;



@RestController
@RequiredArgsConstructor
@Validated
public class MusterController {
    private final MusterServie musterService;


    @PostMapping("/muster/input/gps")
    public ResponseEntity<MusterDto.MusterRes> postMuster(@Valid @RequestBody MusterDto req) {
        return ResponseEntity.ok(musterService.create(req));
    }
    
    @GetMapping("/muster/select/gps")
    public ResponseEntity<MusterDto.MusterRes> getMuster() {
        return musterService.latest().map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.noContent().build());
    }
    

    
}
