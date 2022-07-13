package com.saveme.go.controller;

import com.saveme.go.service.LinkService;
import io.micronaut.http.HttpStatus;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.net.URI;
import java.net.URISyntaxException;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class RedirectLinkControllerTest {

    @Mock
    LinkService linkService;

    @InjectMocks
    RedirectLinkController subject;

    @Test
    void shouldRedirectToOriginalLink() throws URISyntaxException {
        when(linkService.getRedirectURL(anyString())).thenReturn(new URI("https://original"));

        var redirect = subject.redirect("https://short");

        assertSame(HttpStatus.MOVED_PERMANENTLY, redirect.getStatus());
    }
}
