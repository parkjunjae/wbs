package com.wbs.wbs.service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.wbs.wbs.entity.TotalEntity;
import com.wbs.wbs.entity.WeedingEntity;
import com.wbs.wbs.repository.WeedingRepository;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Service
@Data
@RequiredArgsConstructor
public class WeedingService {

    private final WeedingRepository weedingRepository;
    

    public List<WeedingEntity> weedingEntities() {
        return weedingRepository.findByDelYn("N");
    }


    // ✅ 데이터 올 때마다 하트비트 항상 갱신!
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
            newRobot.setIsOnline(true);  // 데이터 오면 무조건 온라인!
            newRobot.setLastHeartbeat(now); // 하트비트 항상 저장
            return weedingRepository.save(newRobot);
        }

        // 기존 등록
        WeedingEntity total = optional.get();

        total.setBattery(weedingEntity.getBattery());
        total.setLatitude(weedingEntity.getLatitude());
        total.setLongitude(weedingEntity.getLongitude());
        total.setIsOnline(true); // 데이터 올 때마다 무조건 온라인!
        total.setDelYn("N");
        total.setLastHeartbeat(now); // 하트비트 항상 갱신

        return weedingRepository.save(total);
    }
    
}
