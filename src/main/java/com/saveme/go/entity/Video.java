package com.saveme.go.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "video")
@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Video {
    @Id
    @GeneratedValue
    private Long id;
    private String title;
    private String description;
    private String videoPath;
    private String videoFileName;
    private Timestamp updated;

    @OneToMany(orphanRemoval = true, fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "video_id", updatable = false)
    private Set<PreFrames> preFrames;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Video)) return false;
        Video video = (Video) o;
        return Objects.equals(getId(), video.getId())
                && Objects.equals(getTitle(), video.getTitle())
                && Objects.equals(getDescription(), video.getDescription())
                && Objects.equals(getVideoPath(), video.getVideoPath())
                && Objects.equals(getVideoFileName(), video.getVideoFileName())
                && Objects.equals(getUpdated(), video.getUpdated())
                && Objects.equals(getPreFrames(), video.getPreFrames());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getTitle(), getDescription(), getVideoPath(), getVideoFileName(), getUpdated(), getPreFrames());
    }
}
