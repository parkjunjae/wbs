package com.wbs.wbs.controller;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;


@RestController
@RequiredArgsConstructor
public class RosbagInfoController {

    @GetMapping("/api/path")
    public ResponseEntity<?> getPath(
        @RequestParam("centerLat") double centerLat,
        @RequestParam("centerLng") double centerLng
    ) throws IOException {
        
        File file = new File("/home/atoz/route_points.json");
        String json = new String(Files.readAllBytes(file.toPath()), StandardCharsets.UTF_8);

        return ResponseEntity.ok()
            .contentType(MediaType.APPLICATION_JSON)
            .body(json);        
    }
    
    
}
