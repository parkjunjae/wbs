package com.wbs.wbs.dto;

import com.wbs.wbs.entity.TotalEntity;
import lombok.Data;

@Data
public class TotalDto {
    private Long id;
    private String hardness;
    private String latitude;
    private String battery;
    private String robotCondition;

    public TotalDto(TotalEntity totalEntity){
        this.id = totalEntity.getId();
        this.hardness = totalEntity.getHardness();
        this.latitude = totalEntity.getLatitude();
        this.battery = totalEntity.getBattery() + "%";
        this.robotCondition = totalEntity.getRobotCondition();

    }

}
