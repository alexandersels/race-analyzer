package com.racing.analyzer.backend.dto.livetiming;

import com.racing.analyzer.backend.enums.LiveTimingState;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LiveTimingDTO {

    @NotNull
    private long id;

    @NotNull
    private String name;

    @NotNull
    private int number;

    @NotNull
    private String car;

    @NotNull
    private String cls;

    @NotNull
    private int position;

    @NotNull
    private LiveTimingState state;

    @NotNull
    private String nationality;

    @NotNull
    private boolean inPit;

    @NotNull
    private long lastTime;

    @NotNull
    private long bestTime;

    @NotNull
    private long sectorOne;

    @NotNull
    private long sectorTwo;

    @NotNull
    private long sectorThree;

    @NotNull
    private long race;

}
