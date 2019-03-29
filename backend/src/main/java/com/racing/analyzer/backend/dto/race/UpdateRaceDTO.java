package com.racing.analyzer.backend.dto.race;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
@Builder
public class UpdateRaceDTO {

    @NotNull
    private long id;

    @NotNull
    private String name;

    @NotNull
    private String url;

    @NotNull
    private boolean isRecording;

    @NotNull
    private int version;

}
