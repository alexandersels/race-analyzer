package com.racing.analyzer.backend.builders;

import com.racing.analyzer.backend.entities.LiveTiming;

public class LiveTimingBuilder {

    private String name;
    private int number;
    private String cls;
    private int position;
    private long lastTime;
    private long bestTime;
    private String nationality;
    private boolean inPit;
    private String car;

    public LiveTimingBuilder withName(String name) {
        this.name = name;
        return this;
    }

    public LiveTimingBuilder withNumber(int number) {
        this.number = number;
        return this;
    }

    public LiveTimingBuilder withCls(String cls) {
        this.cls = cls;
        return this;
    }

    public LiveTimingBuilder withPosition(int position) {
        this.position = position;
        return this;
    }

    public LiveTimingBuilder withLastLapTime(long lastLapTime) {
        this.lastTime = lastLapTime;
        return this;
    }

    public LiveTimingBuilder withBestLapTime(long bestLapTime) {
        this.bestTime = bestLapTime;
        return this;
    }

    public LiveTimingBuilder withNationality(String nationality) {
        this.nationality = nationality;
        return this;
    }

    public LiveTimingBuilder inPit(boolean pitStop) {
        this.inPit = pitStop;
        return this;
    }

    public LiveTimingBuilder withCar(String car) {
        this.car = car;
        return this;
    }

    public LiveTiming build() {
        return new LiveTiming(name, number, cls, position, lastTime, bestTime, nationality, inPit, car);
    }


}
