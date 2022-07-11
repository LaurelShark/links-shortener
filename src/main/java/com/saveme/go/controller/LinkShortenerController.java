package com.saveme.go.controller;

import com.saveme.go.dto.GetShortLinkDto;
import com.saveme.go.dto.LinkDto;
import com.saveme.go.service.LinkService;
import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.Body;
import io.micronaut.http.annotation.Consumes;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Post;

@Controller("/links")
public class LinkShortenerController {

    private LinkService linkService;

    public LinkShortenerController(LinkService linkService) {
        this.linkService = linkService;
    }

    @Post("/short")
    @Consumes(MediaType.APPLICATION_JSON)
    public LinkDto getShortLink(@Body GetShortLinkDto dto) {
        return linkService.getShortLink(dto.getOriginalLink());
    }
}
