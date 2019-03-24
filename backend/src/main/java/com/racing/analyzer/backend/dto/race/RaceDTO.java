package com.racing.analyzer.backend.dto.race;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RaceDTO {

    private long id;
    private String name;
    private boolean recording;
    private String url;
    private int version;

}
