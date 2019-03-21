package com.racing.analyzer.backend.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class RaceDTO {

    private long id;
    private String name;
    private boolean recording;
    private String url;
    private int version;

}



