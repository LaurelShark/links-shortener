package com.saveme.go.controller;

import com.saveme.go.service.LinkService;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;

import java.net.URISyntaxException;

@Controller
public class RedirectLinkController {

    private final LinkService linkService;

    public RedirectLinkController(LinkService linkService) {
        this.linkService = linkService;
    }

    @Get("/{link}")
    public HttpResponse redirect(String link) throws URISyntaxException {
        return HttpResponse.redirect(linkService.getRedirectURL(link));
    }
}
