package com.wbs.wbs.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.wbs.wbs.dto.BoundaryDto;
import com.wbs.wbs.service.RosClientService;


@RestController
@RequestMapping("/api/coverage")
public class CoverageController {
    private final RosClientService rosClientService;

    public CoverageController(RosClientService rosClientService) {
        this.rosClientService = rosClientService;
    }

    @PostMapping("/plan")
    public ResponseEntity<String> receiveBoundary(@RequestBody BoundaryDto boundaryDto) {
        System.out.println("바운더리 수신" + boundaryDto);

        rosClientService.sendBoundaryToRos(boundaryDto);
        
        return ResponseEntity.ok("boundary receive");
    }
    


    
}
