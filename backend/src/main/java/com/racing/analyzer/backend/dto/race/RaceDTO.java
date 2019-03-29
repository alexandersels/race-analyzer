package com.racing.analyzer.backend.dto.race;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class RaceDTO {

    private long id;

    private String name;
    private boolean recording;
    private String url;
    private int version;

    @Builder
    public RaceDTO(long id, String name, boolean recording, String url, int version) {
        this.id = id;
        this.name = name;
        this.recording = recording;
        this.url = url;
        this.version = version;
    }
}
