package com.saveme.go.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LinkDto {
    private String originalLink;
    private String shortLink;
    private Integer timesClicked;
}
