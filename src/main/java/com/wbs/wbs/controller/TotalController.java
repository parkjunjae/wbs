package com.wbs.wbs.controller;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.wbs.wbs.entity.TotalEntity;
import com.wbs.wbs.service.TotalService;

import lombok.RequiredArgsConstructor;

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
}
