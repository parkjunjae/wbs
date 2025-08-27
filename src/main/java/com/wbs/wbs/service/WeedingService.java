package com.wbs.wbs.service;

import java.sql.Timestamp;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.wbs.wbs.entity.WeedingEntity;
import com.wbs.wbs.repository.WeedingRepository;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Service
@EnableScheduling
@Data
@RequiredArgsConstructor
public class WeedingService {

    private final WeedingRepository weedingRepository;

    /** 화면 표시에 사용: 사용중(N)만 */
    public List<WeedingEntity> weedingEntities() {
        return weedingRepository.findByDelYn("N");
    }

    /** delYn 갱신 (필요 시 사용) */
    public Optional<WeedingEntity> updateData(Long id) {
        Optional<WeedingEntity> optional = weedingRepository.findById(id);
        if (optional.isEmpty()) return Optional.empty();
        WeedingEntity e = optional.get();
        e.setDelYn("Y");
        weedingRepository.save(e);
        return Optional.of(e);
    }

    /** 데이터가 들어올 때마다 하트비트/온라인 갱신 */
    public WeedingEntity getMac(WeedingEntity weedingEntity) {
        Optional<WeedingEntity> optional = weedingRepository.findByMac(weedingEntity.getMac());
        Timestamp now = Timestamp.valueOf(LocalDateTime.now());

        if (optional.isEmpty()) {
            // 신규 등록
            WeedingEntity newRobot = new WeedingEntity();
            newRobot.setMac(weedingEntity.getMac());
            newRobot.setBattery(weedingEntity.getBattery());
            newRobot.setLatitude(weedingEntity.getLatitude());
            newRobot.setLongitude(weedingEntity.getLongitude());
            newRobot.setDelYn("N");
            newRobot.setIsOnline(true);        // 데이터 오면 온라인
            newRobot.setLastHeartbeat(now);    // 하트비트 저장
            return weedingRepository.save(newRobot);
        }

        // 기존 등록 갱신
        WeedingEntity robot = optional.get();
        robot.setBattery(weedingEntity.getBattery());
        robot.setLatitude(weedingEntity.getLatitude());
        robot.setLongitude(weedingEntity.getLongitude());
        robot.setIsOnline(true);               // 데이터 오면 온라인
        robot.setDelYn("N");
        robot.setLastHeartbeat(now);           // 하트비트 갱신
        return weedingRepository.save(robot);
    }

    /** 하트비트 기준 20초 이상 수신 없으면 오프라인 처리 */
    @Scheduled(fixedRate = 3000)
    public void checkOnlineStatus() {
        List<WeedingEntity> robots = weedingRepository.findAll();
        LocalDateTime now = LocalDateTime.now();
        boolean changed = false;

        for (WeedingEntity robot : robots) {
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
                    System.out.printf(">> [WEED] OFFLINE 처리: id=%d mac=%s diff=%d초 now=%s%n",
                            robot.getId(), robot.getMac(), diff, now);
                    robot.setIsOnline(false);
                    robot.setDelYn("Y");
                    robot.setLastHeartbeat(Timestamp.valueOf(now));
                    changed = true;
                }
            }
        }

        if (changed) {
            weedingRepository.saveAll(robots);
        }
    }

    /** 전체 조회 (관리용) */
    public List<WeedingEntity> getAll() {
        return weedingRepository.findAll();
    }

    /** delYn 임의 변경 (관리용) */
    public WeedingEntity getDelyn(Long id, String newValue) {
        Optional<WeedingEntity> opt = weedingRepository.findById(id);
        if (opt.isEmpty()) return null;
        WeedingEntity e = opt.get();
        e.setDelYn(newValue);
        return weedingRepository.save(e);
    }
}
