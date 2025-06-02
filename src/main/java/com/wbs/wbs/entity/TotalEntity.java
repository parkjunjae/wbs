package com.wbs.wbs.entity;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.annotations.DynamicUpdate;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "total")
@Getter
@Setter
@ToString(exclude = "mineEntities")
@DynamicUpdate
public class TotalEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "name_id")
    private Long id;

    private String longitude;
    private String latitude;
    
    private String delYn;

    private String mission;

    @Column(name="is_online")
    private Boolean isOnline;

    @Column(name="last_heartbeat")
    private Timestamp lastHeartbeat;

    @Column(name="mac", unique = true)
    private String mac;

    @OneToMany(mappedBy = "totalEntity", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<MineEntity> mineEntities = new ArrayList<>();


}
