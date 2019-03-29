package com.racing.analyzer.backend.dto.statistics;

import com.racing.analyzer.backend.enums.LiveTimingState;
import lombok.Builder;
import lombok.Data;

import java.util.Collection;

@Data
public class AggregatedDriverDTO {

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
    private Collection<AggregatedRoundDTO> rounds;

    @Builder
    public AggregatedDriverDTO(int number, String name, String car, Collection<AggregatedRoundDTO> rounds, int pitStops, long bestLap,
                               long bestSectorOne, long bestSectorTwo, long bestSectorThree, int lastPosition,
                               LiveTimingState lastState) {
        this.number = number;
        this.name = name;
        this.car = car;
        this.rounds = rounds;
        this.pitStops = pitStops;
        this.bestLap = bestLap;
        this.bestSectorOne = bestSectorOne;
        this.bestSectorTwo = bestSectorTwo;
        this.bestSectorThree = bestSectorThree;
        this.lastPosition = lastPosition;
        this.lastState = lastState;
    }
}
