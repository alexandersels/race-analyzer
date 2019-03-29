package com.racing.analyzer.backend.logic.aggregators;

import com.racing.analyzer.backend.dto.statistics.AggregatedDriverDTO;
import com.racing.analyzer.backend.dto.statistics.AggregatedRaceDTO;
import com.racing.analyzer.backend.entities.LiveTiming;

import java.util.Collection;

public class RaceDataAggregator {

    public static AggregatedRaceDTO aggregate(Collection<LiveTiming> liveTimings) {

        Collection<AggregatedDriverDTO> aggregatedDriverData = DriverDataAggregator.aggregate(liveTimings);
        AggregatedRaceDTO raceDTO = AggregatedRaceDTO.builder()
                .drivers(aggregatedDriverData)
                .build();
        return accumulateMetrics(raceDTO, aggregatedDriverData);
    }

    private static AggregatedRaceDTO accumulateMetrics(AggregatedRaceDTO raceDTO,
                                                       Collection<AggregatedDriverDTO> driverData) {

        int amountOfDrivers = 0;
        int amountOfPitStops = 0;
        int amountOfRounds = 0;
        AggregatedDriverDTO winner = null;

        for (AggregatedDriverDTO driver : driverData) {
            amountOfDrivers++;
            amountOfPitStops += driver.getPitStops();
            amountOfRounds += driver.getRounds().size();

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
