package com.saveme.go.service;

import com.saveme.go.entity.Video;
import com.saveme.go.repository.VideoRepository;
import io.micronaut.context.annotation.Bean;
import jakarta.inject.Inject;

import java.util.UUID;

@Bean
public class VideoService {

    @Inject
    private VideoRepository videoRepository;
    public Video findByUUID(Long uuid) {
        return videoRepository.findById(uuid)
                .orElseThrow(() -> new RuntimeException("No such entity"));
    }

    public Video save(String name) {
        return videoRepository.save(name);
    }

}
