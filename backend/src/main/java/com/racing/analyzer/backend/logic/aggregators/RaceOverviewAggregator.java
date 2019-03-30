package com.racing.analyzer.backend.logic.aggregators;

import com.racing.analyzer.backend.dto.statistics.DriverDTO;
import com.racing.analyzer.backend.dto.statistics.RaceOverviewDTO;
import com.racing.analyzer.backend.entities.Race;

import java.util.Collection;

import static com.google.common.base.Preconditions.checkNotNull;

public class RaceOverviewAggregator {

    public static RaceOverviewDTO aggregate(Race race) {
        checkNotNull(race);
        Collection<DriverDTO> aggregatedDriverData = DriverAggregator.aggregate(race.getTimings());
        RaceOverviewDTO raceDTO = RaceOverviewDTO.builder()
                .name(race.getName())
                .drivers(aggregatedDriverData)
                .build();
        return accumulateMetrics(raceDTO, aggregatedDriverData);
    }

    private static RaceOverviewDTO accumulateMetrics(RaceOverviewDTO raceDTO,
                                                     Collection<DriverDTO> driverData) {

        int amountOfDrivers = 0;
        int amountOfPitStops = 0;
        int amountOfRounds = 0;
        DriverDTO winner = null;

        for (DriverDTO driver : driverData) {
            amountOfDrivers++;
            amountOfPitStops += driver.getPitStops();
            amountOfRounds += driver.getAmountOfRounds();

            if (driver.getLastPosition() == 1) {
                winner = driver;
            }
        }

        raceDTO.setAmountOfDrivers(amountOfDrivers);
        raceDTO.setAmountOfPitStops(amountOfPitStops);
        raceDTO.setAmountOfRounds(amountOfRounds);
        raceDTO.setWinner(winner);

        return raceDTO;
    }
}
