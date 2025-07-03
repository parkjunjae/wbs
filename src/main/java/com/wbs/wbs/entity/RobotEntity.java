package com.wbs.wbs.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "total")
@Getter @Setter
public class RobotEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "name_id")
    private Long nameId;

    private Integer battery;
    private String latitude;
    private String longitude;

    @Column(unique = true)
    private String mac;
}
