package com.racing.analyzer.backend.dto.livetiming;

import com.racing.analyzer.backend.enums.LiveTimingState;
import lombok.Builder;
import lombok.Value;

import javax.validation.constraints.NotNull;

@Value
@Builder
public class LiveTimingDTO {

    @NotNull
    public long raceId;
    @NotNull
    private long id;
    private String driverName;
    private int carNumber;
    private String car;
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
}
