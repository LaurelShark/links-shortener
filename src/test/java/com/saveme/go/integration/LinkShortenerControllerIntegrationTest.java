package com.saveme.go.integration;

import com.saveme.go.dto.LinkDto;
import io.micronaut.http.HttpRequest;
import io.micronaut.http.client.HttpClient;
import io.micronaut.http.client.annotation.Client;
import io.micronaut.runtime.server.EmbeddedServer;
import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import jakarta.inject.Inject;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

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
        Assertions.assertThrows(RuntimeException.class, () -> getShortLink(originalLink), "No link provided");
    }

    @Test
    void shouldGenerateShortLinkResponse() {
        var originalLink = "https://original";
        var res = getShortLink(originalLink);
        Assertions.assertEquals(originalLink, res.getOriginalLink());
        Assertions.assertNotNull(res.getShortLink());
    }

    @Test
    void shouldIncrementClickCountOfShortLink() throws InterruptedException {
        var originalLink = "https://www.google.com";
        var beforeClick = getShortLink(originalLink);
        var split = beforeClick.getShortLink().split("/");
        httpClient.toBlocking().retrieve(HttpRequest.GET("/" + split[1]));

        var afterClick = getShortLink(originalLink);

        Assertions.assertEquals(0, beforeClick.getTimesClicked());
        Assertions.assertEquals(1, afterClick.getTimesClicked());
    }

    private LinkDto getShortLink(String originalLink) {
        return httpClient.toBlocking().retrieve(HttpRequest.POST("/links/short",
                LinkDto.builder()
                        .originalLink(originalLink)
                        .build()), LinkDto.class);
    }
}
