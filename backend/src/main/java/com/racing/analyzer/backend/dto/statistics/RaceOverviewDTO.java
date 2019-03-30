package com.racing.analyzer.backend.dto.statistics;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Collection;

@Data
@NoArgsConstructor
public class RaceOverviewDTO {

    private String name;

    private int amountOfDrivers;
    private int amountOfRounds;
    private int amountOfPitStops;
    private DriverDTO winner;
    private Collection<DriverDTO> drivers;

    @Builder
    public RaceOverviewDTO(String name, int amountOfDrivers, int amountOfRounds, int amountOfPitStops, DriverDTO winner, Collection<DriverDTO> drivers) {
        this.name = name;
        this.amountOfDrivers = amountOfDrivers;
        this.amountOfRounds = amountOfRounds;
        this.amountOfPitStops = amountOfPitStops;
        this.winner = winner;
        this.drivers = drivers;
    }
}
