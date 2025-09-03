package com.wbs.wbs.entity;

import java.sql.Blob;

import com.wbs.wbs.dto.MineMediaType;

import jakarta.persistence.Column; 
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name= "mine_media")
@Data
public class MineMediaEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false)
  private String mime;         

  @Column(name = "size_bytes", nullable = false)
  private Long sizeBytes;

  @Column(name = "duration_ms")
  private Integer durationMs;

  @Lob
  @Column(name = "content", columnDefinition = "LONGBLOB", nullable = false)
  private Blob content;        


  @Enumerated(EnumType.STRING)
  @Column(name = "media_type", nullable = false)
  private MineMediaType mediaType;  

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "mine_id", nullable = false)
  private MineEntity mine;
    
}
