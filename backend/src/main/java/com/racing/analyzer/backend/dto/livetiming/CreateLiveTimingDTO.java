package com.racing.analyzer.backend.dto.livetiming;

import com.racing.analyzer.backend.enums.LiveTimingState;
import lombok.Builder;
import lombok.Getter;

import javax.validation.constraints.NotNull;

@Getter
@Builder
public class CreateLiveTimingDTO {

    @NotNull
    private long id;

    @NotNull
    private String driverName;

    @NotNull
    private int carNumber;

    @NotNull
    private String car;

    @NotNull
    private String cls;

    private int position;

    private LiveTimingState state;

    private String nationality;

    private boolean inPit;

    private long lastTime;

    private long bestTime;

    private long sectorOneTime;

    private long sectorTwoTime;

    private long sectorThreeTime;

    @NotNull
    private long race;

}
