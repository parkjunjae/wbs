package com.wbs.wbs.controller;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;  // ★ CrossOrigin, RestController 등

import com.wbs.wbs.dto.ModeFuncRequest;     // ★ 추가 (DTO)
import com.wbs.wbs.entity.TotalEntity;
import com.wbs.wbs.service.TotalService;

import lombok.RequiredArgsConstructor;

@CrossOrigin(origins = "*") // 프론트(포트 3000 등)에서 호출하면 CORS 필요
@RestController
@RequiredArgsConstructor
public class TotalController {
    private final TotalService totalService;

    @GetMapping("/data")
    public ResponseEntity<List<TotalEntity>> totalEntities() {
        List<TotalEntity> getTotal = totalService.totalEntities();
        if (getTotal == null || getTotal.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(getTotal);
    }

    @PutMapping("update/{id}")
    public ResponseEntity<Optional<TotalEntity>> updateData(@PathVariable("id") Long id) {
        Optional<TotalEntity> update = totalService.updateData(id);
        if (update.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(update);
    }

    @PostMapping("/robot/update")
    public ResponseEntity<TotalEntity> postMac(@RequestBody TotalEntity totalEntity) {
        TotalEntity entity = totalService.getMac(totalEntity);
        return ResponseEntity.ok(entity);
    }

    @PutMapping("/robot/update")
    public ResponseEntity<TotalEntity> putMac(@RequestBody TotalEntity totalEntity) {
        TotalEntity entity = totalService.getMac(totalEntity);
        return ResponseEntity.ok(entity);
    }

    @GetMapping("/get")
    public ResponseEntity<List<TotalEntity>> getAll() {
        List<TotalEntity> totalEntity = totalService.getAll();
        if (totalEntity == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(totalEntity);
    }

    @PutMapping("/update-delYn/{id}")
    public ResponseEntity<TotalEntity> putDelyn(@PathVariable("id") Long id, @RequestBody Map<String, String> value) {
        String newValue = value.get("delYn");
        TotalEntity totalEntity = totalService.getDelyn(id, newValue);
        if (totalEntity == null) {
            return ResponseEntity.status(404).build();
        }
        return ResponseEntity.ok(totalEntity);
    }

    // ★★★ 여기 추가: 오퍼레이션에서 모드/미션 저장
    @PostMapping("/robot/mode-func")
    public ResponseEntity<TotalEntity> upsertModeFunc(@RequestBody ModeFuncRequest req) {
        if (req == null || req.getMac() == null || req.getMac().isEmpty()) {
            return ResponseEntity.badRequest().build();
        }
        TotalEntity saved = totalService.upsertModeFunc(req.getMac(), req.getMode(), req.getFunc());
        return ResponseEntity.ok(saved);
    }
}
