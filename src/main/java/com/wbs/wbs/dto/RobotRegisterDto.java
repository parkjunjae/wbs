package com.wbs.wbs.dto;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class RobotRegisterDto {
    private String mac;
    private String latitude;
    private String longitude;
    private Integer battery;
    private String ip;
}