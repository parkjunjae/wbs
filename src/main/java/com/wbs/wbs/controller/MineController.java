package com.wbs.wbs.controller;

import java.io.File;
import java.io.InputStream;
import java.sql.Blob;
import java.util.List;
import java.util.Optional;

import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpRange;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.wbs.wbs.dto.MineMediaType;
import com.wbs.wbs.entity.MineEntity;
import com.wbs.wbs.entity.MineMediaEntity;
import com.wbs.wbs.repository.MineMediaRepository;
import com.wbs.wbs.repository.MineRepository;
import com.wbs.wbs.service.MineService;

import lombok.RequiredArgsConstructor;


@RestController
@RequiredArgsConstructor
public class MineController {

    private final MineService mineService;
    private final MineRepository mineRepository;

    private final MineMediaRepository mediaRepo;


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


    @GetMapping("/media/audio/{mineId}")
    @Transactional(readOnly = true)
    public ResponseEntity<InputStreamResource> streamAudio(@PathVariable Long mineId,
                                                           @RequestHeader HttpHeaders headers) throws Exception {
        return streamFromDb(mineId, MineMediaType.AUDIO, headers);
    }

    @GetMapping("/media/video/{mineId}")
    @Transactional(readOnly = true)
    public ResponseEntity<InputStreamResource> streamVideo(@PathVariable Long mineId,
                                                           @RequestHeader HttpHeaders headers) throws Exception {
        return streamFromDb(mineId, MineMediaType.VIDEO, headers);
    }

    // 공통 스트리밍 로직
    private ResponseEntity<InputStreamResource> streamFromDb(Long mineId, MineMediaType type, HttpHeaders headers) throws Exception {
        MineMediaEntity m = mediaRepo.findByMine_MineIdAndMediaType(mineId, type)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        long fullLen = m.getSizeBytes();
        MediaType contentType = MediaType.parseMediaType(m.getMime());
        Blob blob = m.getContent();

        List<HttpRange> ranges = headers.getRange();
        if (ranges == null || ranges.isEmpty()) {
            // 전체 스트림
            InputStream all = blob.getBinaryStream();
            return ResponseEntity.ok()
                    .contentType(contentType)
                    .contentLength(fullLen)
                    .body(new InputStreamResource(all));
        }

        // Range(구간요청) 처리
        HttpRange r = ranges.get(0);
        long start = r.getRangeStart(fullLen);
        long end   = r.getRangeEnd(fullLen);
        long part  = end - start + 1;

        // Blob.getBinaryStream(offset, length) 의 offset은 1-based
        InputStream slice = blob.getBinaryStream(start + 1, part);

        return ResponseEntity.status(HttpStatus.PARTIAL_CONTENT)
                .header(HttpHeaders.CONTENT_RANGE, String.format("bytes %d-%d/%d", start, end, fullLen))
                .contentType(contentType)
                .contentLength(part)
                .body(new InputStreamResource(slice));
    }


    
}
