package com.racing.analyzer.backend.dto;

import com.racing.analyzer.backend.enums.LiveTimingState;

public class LiveTimingDTO {

    public Long id;
    public String driverName;
    public int carNumber;
    public String car;
    public int position;
    public LiveTimingState state;
    public String nationality;
    public Long lastTime;
    public Long bestTime;
    public Long sectorOneTime;
    public Long sectorTwoTime;
    public Long sectorThreeTime;
    public Long race_if;

}
