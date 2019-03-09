package com.racing.analyzer.backend.builders;

import com.racing.analyzer.backend.entities.LiveTiming;
import com.racing.analyzer.backend.enums.LiveTimingState;

public class LiveTimingBuilder {

    private String name;
    private int number;
    private String cls;
    private int position;
    private int lastTime;
    private int bestTime;
    private String nationality;
    private boolean inPit;
    private String car;
    private LiveTimingState state;
    private int sectorOne;
    private int sectorTwo;
    private int sectorThree;

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

    public LiveTimingBuilder withLastLapTime(int lastLapTime) {
        this.lastTime = lastLapTime;
        return this;
    }

    public LiveTimingBuilder withBestLapTime(int bestLapTime) {
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

    public LiveTimingBuilder withState(LiveTimingState state) {
        this.state = state;
        return this;
    }

    public LiveTimingBuilder withSectorOne(int sectorOne) {
        this.sectorOne = sectorOne;
        return this;
    }

    public LiveTimingBuilder withSectorTwo(int sectorTwo) {
        this.sectorTwo = sectorTwo;
        return this;
    }

    public LiveTimingBuilder withSectorThree(int sectorThree) {
        this.sectorThree = sectorThree;
        return this;
    }

    public LiveTiming build() {
        return new LiveTiming(name, number, cls, position, lastTime, bestTime, nationality, inPit, car, state,
                sectorOne, sectorTwo, sectorThree);
    }

}
