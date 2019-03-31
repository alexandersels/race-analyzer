package com.racing.analyzer.backend.dto.statistics;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Collection;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RaceOverviewDTO {

    private long id;
    private String name;
    private int amountOfDrivers;
    private int amountOfRounds;
    private int amountOfPitStops;
    private DriverDTO winner;
    private Collection<DriverDTO> drivers;

}
