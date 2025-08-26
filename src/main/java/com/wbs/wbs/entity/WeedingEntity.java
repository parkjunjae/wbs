package com.wbs.wbs.entity;

import java.sql.Timestamp;

import org.hibernate.annotations.DynamicUpdate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name= "wedding")
@DynamicUpdate
@Data
public class WeedingEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    private String longitude;
    private String latitude;
    
    private String delYn;

    private String mode;

    @Column(name="is_online")
    private Boolean isOnline;

    @Column(name="last_heartbeat")
    private Timestamp lastHeartbeat;

    @Column(name="mac", unique = true)
    private String mac;

    @Column(name = "battery")
    private Integer battery;

    @Column(name = "ip")
    private String ip;
    
}
