package com.racing.analyzer.backend.dto.race;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateRaceDTO {

    @NotNull
    private String name;

    @NotNull
    private String url;

}
