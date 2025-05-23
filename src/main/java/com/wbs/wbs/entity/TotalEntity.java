package com.wbs.wbs.entity;

import java.sql.Timestamp;

import org.hibernate.annotations.DynamicUpdate;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "total")
@Data
@Getter
@Setter
@DynamicUpdate
public class TotalEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "name_id")
    private Long id;

    private String longitude;
    private String latitude;
    
    private int battery;

    private String delYn;

    private String mission;

    @Column(name="is_online")
    private Boolean isOnline;

    @Column(name="last_heartbeat")
    private Timestamp lastHeartbeat;

    @Column(name="mac", unique = true)
    private String mac;

    @OneToOne(mappedBy="totalEntity")
    @JsonBackReference
    private DetailEntity detailEntity;


}
