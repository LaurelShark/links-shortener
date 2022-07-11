package com.saveme.go.entity;

import lombok.Getter;

import javax.persistence.*;

@Entity
@Table(name = "pre_frames")
@Getter
public class PreFrames {
    @Id
    @GeneratedValue
    private Long id;
    @Column(name = "position")
    private Integer position;
    private String framePath;
    private String frameFileName;

    @Column(name = "video_id", nullable = false, insertable = false, updatable = false)
    private Long videoId;
    @ManyToOne
    @JoinColumn(name = "video_id")
    private Video video;
}
