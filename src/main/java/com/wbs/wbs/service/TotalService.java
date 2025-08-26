package com.wbs.wbs.service;

import java.sql.Timestamp;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.wbs.wbs.entity.TotalEntity;
import com.wbs.wbs.repository.TotalRepository;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Service
@EnableScheduling
@Data
@RequiredArgsConstructor
public class TotalService {

    private final TotalRepository totalRepository;

    public List<TotalEntity> totalEntities() {
        return totalRepository.findByDelYn("N");
    }

    public Optional<TotalEntity> updateData(Long id) {
        Optional<TotalEntity> findOptional = totalRepository.findById(id);
        if (findOptional.isEmpty()) {
            return Optional.empty();
        }
        TotalEntity tEntity = findOptional.get();
        tEntity.setDelYn("Y");
        totalRepository.save(tEntity);
        return Optional.of(tEntity);
    }

    // ✅ 데이터 올 때마다 하트비트 항상 갱신!
    public TotalEntity getMac(TotalEntity totalEntity) {
        Optional<TotalEntity> optional = totalRepository.findByMac(totalEntity.getMac());
        Timestamp now = Timestamp.valueOf(LocalDateTime.now());

        if (optional.isEmpty()) {
            // 신규 등록
            TotalEntity newRobot = new TotalEntity();
            newRobot.setMac(totalEntity.getMac());
            newRobot.setBattery(totalEntity.getBattery());
            newRobot.setLatitude(totalEntity.getLatitude());
            newRobot.setLongitude(totalEntity.getLongitude());
            newRobot.setDelYn("N");
            newRobot.setIsOnline(true);  // 데이터 오면 무조건 온라인!
            newRobot.setLastHeartbeat(now); // 하트비트 항상 저장
            return totalRepository.save(newRobot);
        }

        // 기존 등록
        TotalEntity total = optional.get();

        total.setBattery(totalEntity.getBattery());
        total.setLatitude(totalEntity.getLatitude());
        total.setLongitude(totalEntity.getLongitude());
        total.setIsOnline(true); // 데이터 올 때마다 무조건 온라인!
        total.setDelYn("N");
        total.setLastHeartbeat(now); // 하트비트 항상 갱신

        return totalRepository.save(total);
    }

    // ✅ 하트비트 기준 20초 이상 데이터 없으면 오프라인 판정
    @Scheduled(fixedRate = 3000)
    public void checkOnlineStatus() {
        List<TotalEntity> robots = totalRepository.findAll();
        LocalDateTime now = LocalDateTime.now();
        boolean changed = false;
        for (TotalEntity robot : robots) {
            Timestamp last = robot.getLastHeartbeat();
            if (Boolean.TRUE.equals(robot.getIsOnline())) {
                boolean needOffline = false;
                long diff = -1;
                if (last == null) {
                    needOffline = true;
                } else {
                    diff = Duration.between(last.toLocalDateTime(), now).getSeconds();
                    if (diff > 20) {
                        needOffline = true;
                    }
                }
                if (needOffline) {
                    System.out.printf(">> OFFLINE 처리: id=%d mac=%s diff=%d초 now=%s\n", 
                        robot.getId(), robot.getMac(), diff, now);
                    robot.setIsOnline(false);
                    robot.setDelYn("Y");
                    robot.setLastHeartbeat(Timestamp.valueOf(now));
                    changed = true;
                }
            }
        }
        if (changed) {
            totalRepository.saveAll(robots);
        }
    }

    public List<TotalEntity> getAll() {
        return totalRepository.findAll();
    }

    public TotalEntity getDelyn(Long id, String newValue) {
        Optional<TotalEntity> totalEntity = totalRepository.findById(id);
        if (totalEntity.isEmpty()) {
            return null;
        }
        TotalEntity tEntity = totalEntity.get();
        tEntity.setDelYn(newValue);
        return totalRepository.save(tEntity);
    }
}
