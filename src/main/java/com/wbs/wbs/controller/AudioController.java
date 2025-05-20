package com.wbs.wbs.controller;

import java.util.Map;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.wbs.wbs.service.AudioService;

import lombok.RequiredArgsConstructor;


@RestController
@RequiredArgsConstructor
public class AudioController {
    private final AudioService audioService;
    private volatile String lastSessionId;

    @PostMapping("/audio")
    public ResponseEntity<String> finalizeAudio(@RequestBody Map<String, String> body) {
        String sessionId = body.get("sessionId");

        String detailIdStr = body.get("detailId");

        if (detailIdStr == null || detailIdStr.isBlank()) {
            return ResponseEntity.badRequest().body("Missing or invalid detailId");
        }


        Long detailId = Long.parseLong(detailIdStr);

        // 1. .raw → .wav 변환
        System.out.println("📥 녹음 종료 요청 수신: sessionId=" + sessionId + ", detailId=" + detailId);

        boolean success = audioService.convertRawToWav(sessionId, detailId);

        return success
                ? ResponseEntity.ok("saved")
                : ResponseEntity.status(500).body("conversion failed");
    }

    @GetMapping("/audio/session")
    public Map<String, String> createSession() {
        lastSessionId = UUID.randomUUID().toString();
        return Map.of("sessionId",lastSessionId);
    }

    @GetMapping("/audio/session/latest")
    public Map<String, String> getLastSession() {
        if (lastSessionId == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "세션이 아직 없다.");
        }
        return Map.of("sessionId", lastSessionId);
    }
    
    
}
