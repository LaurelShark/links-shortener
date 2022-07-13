package com.saveme.go.integration;

import com.saveme.go.dto.LinkDto;
import io.micronaut.http.HttpRequest;
import io.micronaut.http.client.HttpClient;
import io.micronaut.http.client.annotation.Client;
import io.micronaut.runtime.server.EmbeddedServer;
import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import jakarta.inject.Inject;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@MicronautTest
class LinkShortenerControllerIntegrationTest {

    @Inject
    EmbeddedServer embeddedServer;

    @Inject
    @Client("/")
    HttpClient httpClient;

    @Test
    void shouldNotGenerateShortLinkResponse() {
        var originalLink = "";
        assertThrows(RuntimeException.class, () -> getShortLink(originalLink), "No link provided");
    }

    @Test
    void shouldGenerateShortLinkResponse() {
        var originalLink = "https://original";
        var res = getShortLink(originalLink);
        assertEquals(originalLink, res.getOriginalLink());
        assertNotNull(res.getShortLink());
    }

    private LinkDto getShortLink(String originalLink) {
        return httpClient.toBlocking().retrieve(HttpRequest.POST("/links/short",
                LinkDto.builder()
                        .originalLink(originalLink)
                        .build()), LinkDto.class);
    }
}
