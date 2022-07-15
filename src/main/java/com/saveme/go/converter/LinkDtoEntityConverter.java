package com.saveme.go.converter;

import com.saveme.go.dto.LinkDto;
import com.saveme.go.entity.Link;
import io.micronaut.context.annotation.Bean;

@Bean
public class LinkDtoEntityConverter {

    public LinkDto toDto(Link link) {
        return LinkDto.builder()
                .originalLink(link.getOriginalLink())
                .shortLink(link.getShortLink())
                .timesClicked(link.getTimesClicked())
                .build();
    }
}
