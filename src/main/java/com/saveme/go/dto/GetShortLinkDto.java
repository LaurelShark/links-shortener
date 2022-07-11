package com.saveme.go.dto;

import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class GetShortLinkDto {
    private String originalLink;
}
