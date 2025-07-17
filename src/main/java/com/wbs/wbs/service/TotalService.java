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

    public TotalEntity getMac(TotalEntity totalEntity) {
        Optional<TotalEntity> optional = totalRepository.findByMac(totalEntity.getMac());
        if (optional.isEmpty()) {
            // 신규 등록
            TotalEntity newRobot = new TotalEntity();
            newRobot.setMac(totalEntity.getMac());
            newRobot.setBattery(totalEntity.getBattery());
            newRobot.setLatitude(totalEntity.getLatitude());
            newRobot.setLongitude(totalEntity.getLongitude());
            newRobot.setDelYn("N");
            newRobot.setIsOnline(totalEntity.getIsOnline());
            if (Boolean.FALSE.equals(totalEntity.getIsOnline())) {
                newRobot.setLastHeartbeat(Timestamp.valueOf(LocalDateTime.now()));
            } else {
                newRobot.setLastHeartbeat(null);
            }
            return totalRepository.save(newRobot);
        }

        // 기존 등록
        TotalEntity total = optional.get();

        boolean wasOnline = Boolean.TRUE.equals(total.getIsOnline());
        boolean nowOnline = Boolean.TRUE.equals(totalEntity.getIsOnline());

        total.setBattery(totalEntity.getBattery());
        total.setLatitude(totalEntity.getLatitude());
        total.setLongitude(totalEntity.getLongitude());

        // online→offline 전환 시에만 lastHeartbeat 기록
        if (wasOnline && !nowOnline) {
            total.setLastHeartbeat(Timestamp.valueOf(LocalDateTime.now()));
        }
        // offline → online 혹은 그대로 online이면 lastHeartbeat를 null로(공란)
        if (nowOnline) {
            total.setLastHeartbeat(null);
        }

        total.setIsOnline(nowOnline);

        return totalRepository.save(total);
    }

@Scheduled(fixedRate = 10000)
public void checkOnlineStatus() {
    System.out.println("스케줄러 동작중!!");
    List<TotalEntity> robots = totalRepository.findAll();
    LocalDateTime now = LocalDateTime.now();
    boolean changed = false;
    for (TotalEntity robot : robots) {
        Timestamp last = robot.getLastHeartbeat();
        Boolean isOnline = robot.getIsOnline();
        // online이면 last_heartbeat가 null이어도 10초 지난 걸로 간주
        if (isOnline != null && isOnline) {
            boolean needOffline = false;
            if (last == null) {
                // last_heartbeat가 null인데 online이면 10초 지난 걸로 판단 (즉시 offline)
                needOffline = true;
            } else {
                Duration diff = Duration.between(last.toLocalDateTime(), now);
                if (diff.getSeconds() > 20) {
                    needOffline = true;
                }
            }
            if (needOffline) {
                robot.setIsOnline(false);
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
