package com.wbs.wbs.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
public class MusterDto {

    private String latitude;
    private String longitude;
    private String cellRow;          // nullable
    private Integer cellNum;         // nullable
    private String source;           // "GRID" | "MAP"
    private Integer qIndex;          // nullable


    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class MusterRes {
        private Long id;
        private String latitude;
        private String longitude;
        private String cellRow;
        private Integer cellNum;
        private String source;
        private Integer qIndex;
        private LocalDateTime createdAt;
    }


    
}


