package com.wbs.wbs.controller;

import java.io.File;
import java.util.List;
import java.util.Optional;

import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.wbs.wbs.entity.MineEntity;
import com.wbs.wbs.repository.MineRepository;
import com.wbs.wbs.service.MineService;

import lombok.RequiredArgsConstructor;


@RestController
@RequiredArgsConstructor
public class MineController {

    private final MineService mineService;
    private final MineRepository mineRepository;


    @GetMapping("/mine")
    public ResponseEntity<List<MineEntity>> getMine(){
        List<MineEntity> getList = mineService.getMine();
        if(getList.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(getList);
    }


    @GetMapping("/play/{mac}")
    public ResponseEntity<Resource> getMethodName(@PathVariable("mac") String mac) {
        Optional<MineEntity> optionalAudio = mineService.getAudio(mac);

        if(optionalAudio.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        MineEntity mineEntity = optionalAudio.get();
        File file = new File(mineEntity.getFilepath());

        if (!file.exists()) {
            return ResponseEntity.noContent().build();
        }

        Resource resource = new FileSystemResource(file);
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType("audio/mpeg"))
                .header(HttpHeaders.CONTENT_DISPOSITION, "inline/filename=\"" + mineEntity.getFilename() + "\"")
                .body(resource);
    }

    


    
}
