package com.saveme.go.controller;

import com.saveme.go.service.LinkService;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import jakarta.inject.Inject;

import java.net.URISyntaxException;

@Controller
public class RedirectLinkController {

    private LinkService linkService;

    public RedirectLinkController(LinkService linkService) {
        this.linkService = linkService;
    }

    @Get("/{link}")
    public HttpResponse redirect(String link) throws URISyntaxException {
        return HttpResponse.redirect(linkService.redirect(link));
    }
}
