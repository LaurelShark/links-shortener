package com.saveme.go.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class LinkDto {
    private String originalLink;
    private String shortLink;
}
