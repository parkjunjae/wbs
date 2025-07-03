package com.wbs.wbs.dto;

public class RobotRegisterDto {
    private String mac;
    private String ip;
    private String robotName;

    public String getMac() { return mac; }
    public void setMac(String mac) { this.mac = mac; }
    public String getIp() { return ip; }
    public void setIp(String ip) { this.ip = ip; }
    public String getRobotName() { return robotName; }
    public void setRobotName(String robotName) { this.robotName = robotName; }
}
