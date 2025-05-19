package com.wbs.wbs.service;

import java.io.File;
import java.time.LocalDateTime;

import org.springframework.stereotype.Service;

import com.wbs.wbs.entity.AudioEntity;
import com.wbs.wbs.entity.DetailEntity;
import com.wbs.wbs.repository.AudioRepository;
import com.wbs.wbs.repository.DetailRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AudioService {

    private final AudioRepository audioRepository;
    private final DetailRepository detailRepository;

    public boolean convertRawToWav(String sessionId, Long detailId) {
        try {
            String wavPath = "uploads/audio-" + sessionId + ".wav";
    
            // 저장 디렉토리 없으면 생성
            new File("uploads").mkdirs();
    
            // 파일 존재 여부 확인
            File wavFile = new File(wavPath);
            if (!wavFile.exists()) {
                System.err.println("❌ WAV 파일 없음: " + wavPath);
                return false;
            }
    
            // DB 저장
            DetailEntity detailEntity = detailRepository.findById(detailId).orElseThrow();
            String filename = wavFile.getName();  // ex: audio-xxx.wav
    
            AudioEntity audioEntity = new AudioEntity(filename, LocalDateTime.now(), detailEntity);
            audioRepository.save(audioEntity);
    
            System.out.println("✅ WAV 파일 DB 저장 완료: " + filename);
            return true;
    
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
}
}
