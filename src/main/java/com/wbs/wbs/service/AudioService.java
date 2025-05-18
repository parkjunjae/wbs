package com.wbs.wbs.service;

import com.wbs.wbs.entity.AudioEntity;
import com.wbs.wbs.entity.DetailEntity;
import com.wbs.wbs.repository.AudioRepository;
import com.wbs.wbs.repository.DetailRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.sound.sampled.AudioFileFormat;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AudioService {

    private final AudioRepository audioRepository;
    private final DetailRepository detailRepository;

    public boolean convertRawToWav(String sessionId, Long detailId) {
        try {
            String rawPath = "uploads/audio-" + sessionId + ".raw";
            String wavPath = "uploads/audio-" + sessionId + ".wav";

            new File("uploads").mkdirs();

            AudioFormat format = new AudioFormat(16000, 16, 1, true, false);
            byte[] rawData = Files.readAllBytes(Paths.get(rawPath));

            ByteArrayInputStream bais = new ByteArrayInputStream(rawData);
            AudioInputStream ais = new AudioInputStream(bais, format, rawData.length / format.getFrameSize());

            AudioSystem.write(ais, AudioFileFormat.Type.WAVE, new File(wavPath));
            System.out.println("✅ WAV 변환 성공: " + wavPath);


            DetailEntity detailEntity = detailRepository.findById(detailId).orElseThrow();
            String filename = "audio-" + sessionId + ".wav";
            AudioEntity audioEntity = new AudioEntity(filename, LocalDateTime.now(), detailEntity);

            audioRepository.save(audioEntity);
            System.out.println("db저장");
            return true;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
