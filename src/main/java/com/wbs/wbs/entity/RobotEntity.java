package com.wbs.wbs.entity;
import jakarta.persistence.*;

@Entity
@Table(name = "robot")

public class RobotEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String mac;

    private String ip;
    private String robotName;

    // Getter/Setter
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getMac() { return mac; }
    public void setMac(String mac) { this.mac = mac; }
    public String getIp() { return ip; }
    public void setIp(String ip) { this.ip = ip; }
    public String getRobotName() { return robotName; }
    public void setRobotName(String robotName) { this.robotName = robotName; }
}
