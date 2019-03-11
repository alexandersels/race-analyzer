package com.racing.analyzer.backend.builders;

import com.racing.analyzer.backend.entities.LiveTiming;
import com.racing.analyzer.backend.entities.Race;
import com.racing.analyzer.backend.enums.LiveTimingState;

public class LiveTimingBuilder extends BaseBuilder<LiveTiming> {

    private String name;
    private int number;
    private String cls;
    private int position;
    private long lastTime;
    private long bestTime;
    private String nationality;
    private boolean inPit;
    private String car;
    private LiveTimingState state;
    private long sectorOne;
    private long sectorTwo;
    private long sectorThree;
    private Race race;

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

    public LiveTimingBuilder withState(LiveTimingState state) {
        this.state = state;
        return this;
    }

    public LiveTimingBuilder withSectorOne(long sectorOne) {
        this.sectorOne = sectorOne;
        return this;
    }

    public LiveTimingBuilder withSectorTwo(long sectorTwo) {
        this.sectorTwo = sectorTwo;
        return this;
    }

    public LiveTimingBuilder withSectorThree(long sectorThree) {
        this.sectorThree = sectorThree;
        return this;
    }

    public LiveTimingBuilder withRace(Race race) {
        this.race = race;
        return this;
    }

    @Override
    protected LiveTiming createInstance() {
        return new LiveTiming(name, number, cls, position, lastTime, bestTime, nationality, inPit, car, state,
                sectorOne, sectorTwo, sectorThree, race);
    }

}
