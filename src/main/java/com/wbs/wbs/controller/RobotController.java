package com.wbs.wbs.controller;

import com.wbs.wbs.dto.RobotRegisterDto;
import com.wbs.wbs.entity.RobotEntity;
import com.wbs.wbs.repository.RobotRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/robot")
public class RobotController {
    @Autowired
    private RobotRepository robotRepository;

    @PostMapping("/register")
    public ResponseEntity<String> registerRobot(@RequestBody RobotRegisterDto dto) {
        RobotEntity robot = robotRepository.findByMac(dto.getMac())
            .orElse(new RobotEntity());
        robot.setMac(dto.getMac());
        robot.setIp(dto.getIp());
        robot.setRobotName(dto.getRobotName());
        robotRepository.save(robot);
        return ResponseEntity.ok("등록 또는 갱신 완료");
    }
}