package com.wbs.wbs.entity;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name= "muster")
@Data
public class MusterEntity {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name="muster_id")
    private Long musterId;

    private String latitude;
    private String longitude;

    @Column(name = "cell_row")
    private String cellRow;

    @Column(name = "cell_num")
    private Integer cellNum;

    @Column(name = "q_index")
    private Integer qIndex;

    @Enumerated(EnumType.STRING)
    @Column(name = "source", length = 10, nullable = false)
    private ClickSource source;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    public enum ClickSource { GRID, MAP }


}
