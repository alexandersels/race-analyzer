package com.racing.analyzer.backend.dto.statistics;

import com.racing.analyzer.backend.enums.LiveTimingState;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@NoArgsConstructor
@SuperBuilder
public class DriverDTO {

    private int number;
    private String name;
    private String car;
    private int pitStops;
    private int lastPosition;
    private LiveTimingState lastState;
    private long bestLap;
    private long bestSectorOne;
    private long bestSectorTwo;
    private long bestSectorThree;
    private long amountOfRounds;

    public DriverDTO(int number, String name, String car, int pitStops, long bestLap,
                     long bestSectorOne, long bestSectorTwo, long bestSectorThree, int lastPosition,
                     LiveTimingState lastState, long amountOfRounds) {
        this.number = number;
        this.name = name;
        this.car = car;
        this.pitStops = pitStops;
        this.bestLap = bestLap;
        this.bestSectorOne = bestSectorOne;
        this.bestSectorTwo = bestSectorTwo;
        this.bestSectorThree = bestSectorThree;
        this.lastPosition = lastPosition;
        this.lastState = lastState;
        this.amountOfRounds = amountOfRounds;
    }
}
