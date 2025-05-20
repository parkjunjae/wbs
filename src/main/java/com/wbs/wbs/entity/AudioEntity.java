package com.wbs.wbs.entity;


import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonBackReference;

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
@Table(name = "robot_audio")
public class AudioEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "audio_id")
    private Long audioId;

    @Column(name = "file_name")
    private String filename;

    private String duration;

    private String format;

    private LocalDateTime time;

    @ManyToOne
    @JoinColumn(name = "detail_id")
    @JsonBackReference
    private DetailEntity detailEntity;

    protected AudioEntity(){

    }


    public AudioEntity(String filename, LocalDateTime time, DetailEntity detailEntity ){
        this.filename = filename;
        this.time = time;
        this.detailEntity = detailEntity;
    }

}
