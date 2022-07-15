package com.saveme.go.service;

import com.saveme.go.converter.LinkDtoEntityConverter;
import com.saveme.go.dto.LinkDto;
import com.saveme.go.entity.Link;
import com.saveme.go.repository.LinkRepository;
import io.micronaut.context.annotation.Bean;
import io.micronaut.context.annotation.Value;

import java.net.URI;
import java.net.URISyntaxException;

@Bean
public class LinkService {

    public static final String URI_NOT_FOUND = "Redirect URI not found!";
    private final LinkRepository linkRepository;
    private final CodecService codecService;
    private final LinkDtoEntityConverter linkDtoEntityConverter;
    @Value("${micronaut.server.host}")
    private String host;
    @Value("${micronaut.server.port}")
    private String port;

    public LinkService(LinkRepository linkRepository, CodecService codecService,
                       LinkDtoEntityConverter linkDtoEntityConverter) {
        this.linkRepository = linkRepository;
        this.codecService = codecService;
        this.linkDtoEntityConverter = linkDtoEntityConverter;
    }

    public URI getRedirectURL(String link) throws URISyntaxException {
        var fromDb = linkRepository.getOriginalByShort(link);
        if (originalLinkExists(fromDb)) {
            URI redirectUri = new URI(fromDb.getOriginalLink());
            incrementClickCountAndSave(fromDb);
            return redirectUri;
        } else {
            throw new RuntimeException(URI_NOT_FOUND);
        }
    }

    public LinkDto getShortLink(String link) {
        if (link == null) {
            throw new RuntimeException("No link provided");
        }
        var shortByOriginal = linkRepository.getShortByOriginal(link);
        LinkDto linkDto = shortByOriginal != null
                ? linkDtoEntityConverter.toDto(shortByOriginal)
                : linkDtoEntityConverter.toDto(generateNewLink(link));
        linkDto.setShortLink(toServerLink(linkDto.getShortLink()));
        return linkDto;
    }

    private boolean originalLinkExists(Link link) {
        return link != null && link.getOriginalLink() != null;
    }

    private Link generateNewLink(String link) {
        var hash = codecService.generateLink();
        return linkRepository.save(Link.builder()
                .originalLink(link)
                .shortLink(hash)
                .timesClicked(0)
                .build());
    }

    private String toServerLink(String hash) {
        return host + ":" + port + "/" + hash;
    }

    private void incrementClickCountAndSave(Link fromDb) {
        fromDb.setTimesClicked(fromDb.getTimesClicked() + 1);
        linkRepository.save(fromDb);
    }
}
