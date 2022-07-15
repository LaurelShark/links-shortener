package com.saveme.go.integration;

import com.saveme.go.dto.LinkDto;
import io.micronaut.http.HttpRequest;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.HttpStatus;
import io.micronaut.http.client.HttpClient;
import io.micronaut.http.client.annotation.Client;
import io.micronaut.runtime.server.EmbeddedServer;
import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import jakarta.inject.Inject;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import javax.persistence.EntityManager;

@MicronautTest
class RedirectLinkControllerIntegrationTest {

    @Inject
    EmbeddedServer embeddedServer;
    @Inject
    @Client("/")
    HttpClient httpClient;
    @Inject
    EntityManager entityManager;

    @Test
    void shouldNotRedirect_whenShortLinkNotFound() {
        var nonExisting = "";
        Assertions.assertThrows(RuntimeException.class, () -> redirect(nonExisting), "No link provided");
    }

    @Test
    void shouldGenerateShortLinkResponse() throws InterruptedException {
        var originalLink = "https://www.google.com";
        var generatedLink = httpClient.toBlocking().retrieve(HttpRequest.POST("/links/short",
                LinkDto.builder()
                        .originalLink(originalLink)
                        .build()), LinkDto.class);
        var split = generatedLink.getShortLink().split("/");

        var res = redirect(split[1]);
        Assertions.assertSame(HttpStatus.OK, res.getStatus());
        Assertions.assertEquals("gws", res.getHeaders().get("Server"));
    }

    private HttpResponse<Object> redirect(String shortLink) {
        return httpClient.toBlocking().exchange(HttpRequest.GET("/" + shortLink));
    }
}
