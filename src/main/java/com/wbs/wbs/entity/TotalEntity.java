package com.wbs.wbs.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Entity
@Table(name = "total")
@Data
public class TotalEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "name_id")
    private Long id;

    private String hardness;
    private String latitude;
    private int battery;

    @Column(name = "robot_condition")
    private String robotCondition;


}
