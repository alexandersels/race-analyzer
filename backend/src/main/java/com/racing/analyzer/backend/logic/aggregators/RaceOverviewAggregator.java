package com.racing.analyzer.backend.logic.aggregators;

import com.racing.analyzer.backend.dto.statistics.DriverDTO;
import com.racing.analyzer.backend.dto.statistics.RaceOverviewDTO;
import com.racing.analyzer.backend.entities.Race;
import com.racing.analyzer.backend.enums.LiveTimingState;

import java.util.Collection;

import static com.google.common.base.Preconditions.checkNotNull;

public class RaceOverviewAggregator {

    public static RaceOverviewDTO aggregate(Race race) {
        checkNotNull(race);
        RaceOverviewDTO raceDTO = initialDto(race);
        Collection<DriverDTO> drivers = DriverAggregator.aggregate(race);
        raceDTO.setDrivers(drivers);
        return accumulateMetrics(raceDTO, drivers);
    }

    private static RaceOverviewDTO accumulateMetrics(RaceOverviewDTO raceDTO,
                                                     Collection<DriverDTO> driverData) {

        int drivers = 0;
        int pitStops = 0;
        int rounds = 0;
        DriverDTO winner = null;

        for (DriverDTO driver : driverData) {
            drivers++;
            pitStops += driver.getPitStops();
            rounds += driver.getAmountOfRounds();

            if (driver.getLastPosition() == 1 && driver.getLastState() == LiveTimingState.FINISHED) {
                winner = driver;
            }
        }

        raceDTO.setAmountOfDrivers(drivers);
        raceDTO.setAmountOfPitStops(pitStops);
        raceDTO.setAmountOfRounds(rounds);
        raceDTO.setWinner(winner);

        return raceDTO;
    }

    private static RaceOverviewDTO initialDto(Race race) {
        return RaceOverviewDTO.builder()
                .id(race.getId())
                .name(race.getName())
                .amountOfDrivers(0)
                .amountOfPitStops(0)
                .amountOfRounds(0)
                .build();
    }
}
