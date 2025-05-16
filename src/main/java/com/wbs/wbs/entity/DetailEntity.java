package com.wbs.wbs.entity;

import java.sql.Timestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
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

    private String image;

    private String video;

    private String comment;

    private Timestamp registration;


    @ManyToOne
    @JoinColumn(name="name_id")
    TotalEntity totalEntity;

    
}
