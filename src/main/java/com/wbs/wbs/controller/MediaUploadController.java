// MediaUploadController.java
package com.wbs.wbs.controller;

import org.hibernate.engine.jdbc.BlobProxy;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.wbs.wbs.dto.MineMediaType;
import com.wbs.wbs.entity.MineEntity;
import com.wbs.wbs.entity.MineMediaEntity;
import com.wbs.wbs.repository.MineMediaRepository;
import com.wbs.wbs.repository.MineRepository;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/mines")
public class MediaUploadController {

  private final MineRepository mineRepo;
  private final MineMediaRepository mediaRepo;

  @PutMapping("/{mineId}/audio")
  @Transactional
  public ResponseEntity<?> uploadAudio(@PathVariable Long mineId,
                                       @RequestParam("file") MultipartFile file,
                                       @RequestParam(required = false) Integer durationMs) throws Exception {
    MineEntity mine = mineRepo.findById(mineId).orElseThrow();
    MineMediaEntity media = mediaRepo
        .findByMine_MineIdAndMediaType(mineId, MineMediaType.AUDIO)
        .orElseGet(MineMediaEntity::new);

    media.setMine(mine);
    media.setMediaType(MineMediaType.AUDIO);
    media.setMime(file.getContentType() != null ? file.getContentType() : "audio/wav");
    media.setSizeBytes(file.getSize());
    media.setDurationMs(durationMs);
    media.setContent(BlobProxy.generateProxy(file.getInputStream(), file.getSize())); // ✅ DB LOB 저장

    mediaRepo.save(media);
    return ResponseEntity.ok().build();
  }

  @PutMapping("/{mineId}/video")
  @Transactional
  public ResponseEntity<?> uploadVideo(@PathVariable Long mineId,
                                       @RequestParam("file") MultipartFile file,
                                       @RequestParam(required = false) Integer durationMs) throws Exception {
    MineEntity mine = mineRepo.findById(mineId).orElseThrow();
    MineMediaEntity media = mediaRepo
        .findByMine_MineIdAndMediaType(mineId, MineMediaType.VIDEO)
        .orElseGet(MineMediaEntity::new);

    media.setMine(mine);
    media.setMediaType(MineMediaType.VIDEO);
    media.setMime(file.getContentType() != null ? file.getContentType() : "video/mp4");
    media.setSizeBytes(file.getSize());
    media.setDurationMs(durationMs);
    media.setContent(BlobProxy.generateProxy(file.getInputStream(), file.getSize())); // ✅ DB LOB 저장

    mediaRepo.save(media);
    return ResponseEntity.ok().build();
  }
}
