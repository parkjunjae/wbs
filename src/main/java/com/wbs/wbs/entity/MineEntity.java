package com.wbs.wbs.entity;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "mine")
@Getter
@Setter
@ToString(exclude = "totalEntity")
public class MineEntity {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name="mine_id")
    private Long mineId;

    private String latitude;

    private String longitude;

    @Column(name = "file_name")
    private String filename;

    @Column(name="file_path")
    private String filepath;
    
    private LocalDateTime time;

    @Column(name="mine_removal_status")
    private String mineRemovalStatus;

    private String fileVedio;

    @ManyToOne(fetch=FetchType.EAGER)
    @JoinColumn(name = "name_id")
    @JsonBackReference 
    private TotalEntity totalEntity;



    
}
