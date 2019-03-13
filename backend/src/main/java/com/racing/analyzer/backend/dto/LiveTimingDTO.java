package com.racing.analyzer.backend.dto;

import com.racing.analyzer.backend.enums.LiveTimingState;

public class LiveTimingDTO {

    public long id;
    public String driverName;
    public int carNumber;
    public String car;
    public int position;
    public LiveTimingState state;
    public String nationality;
    public long lastTime;
    public long bestTime;
    public long sectorOneTime;
    public long sectorTwoTime;
    public long sectorThreeTime;
    public long race_if;

}
