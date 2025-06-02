package com.wbs.wbs.entity;

import java.sql.Timestamp;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
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

    private String video;

    private String comments;

    private Timestamp registration;


    @OneToOne
    @JoinColumn(name="name_id", referencedColumnName = "name_id")
    @JsonManagedReference
    TotalEntity totalEntity;




}
