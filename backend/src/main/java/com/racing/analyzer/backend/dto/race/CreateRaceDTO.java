package com.racing.analyzer.backend.dto.race;

import lombok.Builder;
import lombok.Getter;

import javax.validation.constraints.NotNull;

@Getter
@Builder
public class CreateRaceDTO {

    @NotNull
    private String name;

    @NotNull
    private String url;
}
