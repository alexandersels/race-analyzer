package com.racing.analyzer.backend.dto.race;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
@Builder
public class CreateRaceDTO {

    @NotNull
    private String name;

    @NotNull
    private String url;
}
