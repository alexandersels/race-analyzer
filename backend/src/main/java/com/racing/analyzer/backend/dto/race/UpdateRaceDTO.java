package com.racing.analyzer.backend.dto.race;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UpdateRaceDTO implements Serializable {

    @NotNull
    private long id;

    @NotNull
    private String name;

    @NotNull
    private String url;

    @NotNull
    private int version;

}
