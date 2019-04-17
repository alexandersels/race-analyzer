package com.racing.analyzer.backend.dto.race;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import javax.validation.constraints.NotNull;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RaceDTO implements Serializable {

    @NotNull
    private long id;

    @NotNull
    private String name;

    @NotNull
    private boolean recording;

    @NotNull
    private String url;

    @NotNull
    private int version;

}
