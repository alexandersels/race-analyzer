package com.racing.analyzer.backend.dto.race;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
public class UpdateRaceDTO {

    @NotNull
    private long id;

    @NotNull
    private String name;

    @NotNull
    private String url;

    @NotNull
    private boolean recording;

    @NotNull
    private int version;

    @Builder
    public UpdateRaceDTO(long id, String name, String url, boolean recording, int version) {
        this.id = id;
        this.name = name;
        this.url = url;
        this.recording = recording;
        this.version = version;
    }

}
