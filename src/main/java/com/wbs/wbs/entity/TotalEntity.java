package com.wbs.wbs.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "total")
@Data
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

    @Column(name = "robot_condition")
    private String robotCondition;


}
