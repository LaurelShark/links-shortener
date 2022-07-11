package com.saveme.go.integration;

import com.saveme.go.entity.Video;
import io.micronaut.http.HttpRequest;
import io.micronaut.http.client.HttpClient;
import io.micronaut.http.client.annotation.Client;
import io.micronaut.runtime.server.EmbeddedServer;
import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import jakarta.inject.Inject;
import org.junit.jupiter.api.Test;

import javax.persistence.EntityManager;
import java.sql.Timestamp;
import java.util.HashSet;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@MicronautTest
class VideoControllerIntegrationTest {

    @Inject
    EmbeddedServer embeddedServer;

    @Inject
    @Client("/")
    HttpClient httpClient;

    @Inject
    EntityManager entityManager;

    @Test
    void shouldStringResponse() {
        String response = httpClient.toBlocking()
                .retrieve(HttpRequest.GET("/19L"));
        assertEquals("String", response);
    }

    @Test
    void shouldFindVideoById() {
        var now = new Timestamp(System.nanoTime());
        Video video = new Video(1L, "test", "test", "test",
                "test", now, new HashSet<>());
        entityManager.merge(video);
        entityManager.getTransaction().commit();

        var response = httpClient.toBlocking()
                .retrieve(HttpRequest.GET("/videos/1"), Video.class);

        assertEquals(1, (long) response.getId());
        assertTrue(response.getUpdated().compareTo(now) == 0);
        assertEquals("test", response.getTitle());
        assertEquals("test", response.getDescription());
        assertEquals("test", response.getVideoPath());
        assertEquals("test", response.getVideoFileName());
    }
}
