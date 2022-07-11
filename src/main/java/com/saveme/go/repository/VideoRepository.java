package com.saveme.go.repository;

import com.saveme.go.entity.Video;

import javax.validation.constraints.NotBlank;
import java.util.Optional;

public interface VideoRepository {
    Optional<Video> findById(Long id);

    Video save(@NotBlank String name);
}
