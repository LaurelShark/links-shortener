package com.saveme.go.controller;

import com.saveme.go.dto.GetShortLinkDto;
import com.saveme.go.dto.LinkDto;
import com.saveme.go.service.LinkService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class LinkShortenerControllerTest {

    @Mock
    LinkService linkService;

    @InjectMocks
    LinkShortenerController subject;

    @Test
    void shouldShortenOriginalLink() {
        var originalLink = "https://github.com/LaurelShark/links-shortener";
        var shortLink = "https://short.ly";
        var mockedResponse = LinkDto.builder()
                .originalLink(originalLink)
                .shortLink(shortLink)
                .build();
        when(linkService.getShortLink(originalLink)).thenReturn(mockedResponse);

        var res = subject.getShortLink(GetShortLinkDto.builder()
                .originalLink(originalLink)
                .build());

        assertEquals(res.getOriginalLink(), originalLink);
        assertEquals(res.getShortLink(), shortLink);
    }
}
