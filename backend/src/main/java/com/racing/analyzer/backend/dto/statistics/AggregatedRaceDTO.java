package com.racing.analyzer.backend.dto.statistics;

import lombok.Builder;
import lombok.Data;

import java.util.Collection;

@Data
public class AggregatedRaceDTO {

    private int amountOfDrivers;
    private int amountOfRounds;
    private int amountOfPitStops;
    private AggregatedDriverDTO winner;
    private Collection<AggregatedDriverDTO> drivers;

    @Builder
    public AggregatedRaceDTO(int amountOfDrivers, int amountOfRounds, int amountOfPitStops, AggregatedDriverDTO winner, Collection<AggregatedDriverDTO> drivers) {
        this.amountOfDrivers = amountOfDrivers;
        this.amountOfRounds = amountOfRounds;
        this.amountOfPitStops = amountOfPitStops;
        this.winner = winner;
        this.drivers = drivers;
    }
}
