package com.racing.analyzer.backend.dto.race;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
public class CreateRaceDTO {

    @NotNull
    private String name;

    @NotNull
    private String url;

    @Builder
    public CreateRaceDTO(String name, String url) {
        this.name = name;
        this.url = url;
    }
}
