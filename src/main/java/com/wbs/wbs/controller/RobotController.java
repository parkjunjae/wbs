package com.wbs.wbs.controller;

import com.wbs.wbs.dto.RobotRegisterDto;
import com.wbs.wbs.entity.RobotEntity;
import com.wbs.wbs.repository.RobotRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/robot")
public class RobotController {

    @Autowired
    private RobotRepository robotRepository;

    // 로봇 등록 또는 갱신 API
    @PostMapping("/register")
    public ResponseEntity<String> registerRobot(@RequestBody RobotRegisterDto dto) {
        RobotEntity robot = robotRepository.findByMac(dto.getMac()).orElse(new RobotEntity());
        robot.setMac(dto.getMac());
        robot.setLatitude(dto.getLatitude());
        robot.setLongitude(dto.getLongitude());
        robot.setBattery(dto.getBattery());
        robot.setIp(dto.getIp());
        robotRepository.save(robot);
        return ResponseEntity.ok("등록 또는 갱신 완료");
    }

    // ★ 로봇 전체 목록 조회 (battery 반드시 포함) + 실행 로그 남기기
    @GetMapping("/list")
    public ResponseEntity<List<RobotRegisterDto>> getRobots() {
        System.out.println("===== /api/robot/list 컨트롤러 실행됨 =====");
        List<RobotEntity> robots = robotRepository.findAll();
        List<RobotRegisterDto> dtos = robots.stream().map(entity -> {
            RobotRegisterDto dto = new RobotRegisterDto();
            dto.setMac(entity.getMac());
            dto.setLatitude(entity.getLatitude());
            dto.setLongitude(entity.getLongitude());
            dto.setIp(entity.getIp());
            dto.setBattery(entity.getBattery()); // ⭐️ 반드시 battery 포함!
            return dto;
        }).collect(Collectors.toList());
        System.out.println("===== 반환할 DTO 리스트: =====");
        for (RobotRegisterDto d : dtos) {
            System.out.println("mac=" + d.getMac() + ", battery=" + d.getBattery());
        }
        return ResponseEntity.ok(dtos);
    }
}
