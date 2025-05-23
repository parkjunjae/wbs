package com.wbs.wbs.service;

import java.sql.Timestamp;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.wbs.wbs.entity.TotalEntity;
import com.wbs.wbs.repository.TotalRepository;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Service
@Data
@RequiredArgsConstructor
public class TotalService {

    private final TotalRepository totalRepository;

    public List<TotalEntity> totalEntities(){
        List<TotalEntity> list = totalRepository.findByDelYnOrderByBatteryDesc("N");
        return list;
    }

    

    public Optional<TotalEntity> updateData(Long id){
        Optional<TotalEntity> findOptional = totalRepository.findById(id);
        if (findOptional.isEmpty()) {
            return Optional.empty();
        }
        
        TotalEntity tEntity = findOptional.get();
        tEntity.setDelYn("Y");
        totalRepository.save(tEntity);

        return Optional.of(tEntity);
    }


    public TotalEntity getMac(TotalEntity totalEntity){
        Optional<TotalEntity> optional = totalRepository.findByMac(totalEntity.getMac());
        if(optional.isEmpty()) {
            TotalEntity newRobot = new TotalEntity();
            newRobot.setMac(totalEntity.getMac());
            newRobot.setBattery(totalEntity.getBattery());
            newRobot.setLatitude(totalEntity.getLatitude());
            newRobot.setLongitude(totalEntity.getLongitude());
            newRobot.setDelYn("Y");
            newRobot.setLastHeartbeat(Timestamp.valueOf(LocalDateTime.now()));
            newRobot.setIsOnline(true);

            return totalRepository.save(newRobot);
        }

        TotalEntity total = optional.get();
        total.setBattery(totalEntity.getBattery());
        total.setDelYn("N");
        total.setLatitude(totalEntity.getLatitude());
        total.setLongitude(totalEntity.getLongitude());
        total.setLastHeartbeat(Timestamp.valueOf(LocalDateTime.now()));
        total.setIsOnline(true);
        return totalRepository.save(total);
    }

    @Scheduled(fixedRate = 3000)
    public void checkRobotConnections() {
        List<TotalEntity> robots = totalRepository.findAll();
        LocalDateTime now = LocalDateTime.now();

        for (TotalEntity robot : robots) {
            Timestamp last = robot.getLastHeartbeat();

            boolean isOnline = last != null &&
                Duration.between(last.toLocalDateTime(), now).getSeconds() <= 3;

            robot.setIsOnline(isOnline);

            if (robot.getDelYn() == null) {
                robot.setDelYn("N");
            }
        
        }

        totalRepository.saveAll(robots);
    }


}
