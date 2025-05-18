package com.wbs.wbs.controller;

import com.wbs.wbs.service.AudioService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequiredArgsConstructor
public class AudioController {
    private final AudioService audioService;

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
}
