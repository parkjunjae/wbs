package com.wbs.wbs.entity;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name= "robot_detail")
public class DetailEntity {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name="detail_id")
    private Long detailId;

    private String type;

    private String delYn;

    private String video;

    private String comment;

    private Timestamp registration;


    @ManyToOne
    @JoinColumn(name="name_id")
    TotalEntity totalEntity;

    @OneToMany(mappedBy = "detailEntity", cascade = CascadeType.ALL)
    private List<AudioEntity> audioEntities;
}
