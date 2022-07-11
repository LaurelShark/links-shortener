package com.saveme.go.repository.impl;

import com.saveme.go.entity.Video;
import com.saveme.go.repository.VideoRepository;
import io.micronaut.transaction.annotation.ReadOnly;
import jakarta.inject.Singleton;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import javax.validation.constraints.NotBlank;
import java.util.Optional;

@Singleton
public class VideoRepositoryImpl implements VideoRepository {
    private final EntityManager entityManager;

    public VideoRepositoryImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    @ReadOnly
    public Optional<Video> findById(Long id) {
        return Optional.ofNullable(entityManager.find(Video.class, id));
    }

    @Override
    @Transactional // <4>
    public Video save(@NotBlank String name) {
        Video genre = Video.builder()
                .videoPath(name)
                .build();
        entityManager.persist(genre);
        return genre;
    }
}
