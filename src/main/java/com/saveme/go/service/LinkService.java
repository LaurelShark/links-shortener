package com.saveme.go.service;

import com.saveme.go.dto.LinkDto;
import com.saveme.go.entity.Link;
import com.saveme.go.repository.LinkRepository;
import io.micronaut.context.annotation.Bean;
import io.micronaut.context.annotation.Value;

import java.net.URI;
import java.net.URISyntaxException;

@Bean
public class LinkService {

    @Value("${micronaut.server.host}")
    private String host;
    @Value("${micronaut.server.port}")
    private String port;

    private LinkRepository linkRepository;
    private CodecService codecService;

    public LinkService(LinkRepository linkRepository, CodecService codecService) {
        this.linkRepository = linkRepository;
        this.codecService = codecService;
    }

    public URI redirect(String link) throws URISyntaxException {
        var original = linkRepository.getOriginalByShort(link);
        URI redirectUri = null;
        if (original != null) {
            var originalLink = original.getOriginalLink();
            if (originalLink != null) {
                redirectUri = new URI(originalLink);
            }
        } else {
            throw new RuntimeException("Redirect URI not found!");
        }
        return redirectUri;
    }

    public LinkDto getShortLink(String link) {
        var shortByOriginal = linkRepository.getShortByOriginal(link);
        return LinkDto.builder()
                .shortLink(getShortLink(link, shortByOriginal))
                .originalLink(link)
                .build();
    }

    private String getShortLink(String link, Link shortByOriginal) {
        String shortLink = shortByOriginal != null
                ? shortByOriginal.getShortLink()
                : generateShortLink(link);
        return host + ":" + port + "/" + shortLink;
    }

    private String generateShortLink(String link) {
        var generated = codecService.generateLink();
        linkRepository.save(Link.builder()
                .originalLink(link)
                .shortLink(generated)
                .build());
        return generated;
    }
}
