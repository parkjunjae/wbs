package com.wbs.wbs.entity;


import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

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
    private DetailEntity detailEntity;

    protected AudioEntity(){

    }


    public AudioEntity(String filename, LocalDateTime time, DetailEntity detailEntity ){
        this.filename = filename;
        this.time = time;
        this.detailEntity = detailEntity;
    }

}
