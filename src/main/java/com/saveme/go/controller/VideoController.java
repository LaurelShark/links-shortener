package com.saveme.go.controller;

import com.saveme.go.entity.Video;
import com.saveme.go.service.VideoService;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.PathVariable;
import io.micronaut.http.annotation.Post;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.inject.Inject;

@Controller("/videos")
public class VideoController {

    private VideoService videoService;

    public VideoController(VideoService videoService) {
        this.videoService = videoService;
    }

    @Get("/{uuid}")
    public Video findById(@PathVariable Long uuid) {
        return videoService.findByUUID(uuid);
    }


    @Operation(summary = "Creates a new bar object adding a decorated id and the current time",
            description = "Showcase of the creation of a dto")
    @ApiResponse(responseCode = "201", description = "Bar object correctly created",
            content = @Content(mediaType = "application/json",
                    schema = @Schema(type = "BarDto")))
    @ApiResponse(responseCode = "400", description = "Invalid id Supplied")
    @ApiResponse(responseCode = "500", description = "Remote error, server is going nuts")
    @Tag(name = "create")
    @Post("/{name}")
    public Video save(@PathVariable String name) {
        return videoService.save(name);
    }
}
