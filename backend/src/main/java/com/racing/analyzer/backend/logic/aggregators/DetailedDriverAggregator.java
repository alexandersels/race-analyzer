package com.racing.analyzer.backend.logic.aggregators;

import com.google.common.collect.Iterables;
import com.racing.analyzer.backend.dto.statistics.DetailedDriverDTO;
import com.racing.analyzer.backend.dto.statistics.RoundDTO;
import com.racing.analyzer.backend.entities.LiveTiming;
import com.racing.analyzer.backend.entities.Race;
import com.racing.analyzer.backend.enums.LiveTimingState;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;


public class DetailedDriverAggregator {

    /**
     * Creates a collection of aggregated driver data based on a collection of live timing data.
     *
     * @param race a given collection of live timing data.
     * @return a collection of aggregated information driver data.
     */
    public static Collection<DetailedDriverDTO> aggregateDetailedInfo(Race race) {

        Map<Integer, List<LiveTiming>> timingsPerDriver = race.getTimings().stream().
                collect(Collectors.groupingBy(LiveTiming::getNumber));

        return timingsPerDriver.entrySet().stream()
                .map(kv -> kv.getValue())
                .map(timings -> aggregate(race, timings))
                .collect(toList());
    }

    public static DetailedDriverDTO aggregateDetailedDriverInfo(Race race, int number) {
        List<LiveTiming> filteredTimings = race.getTimings().stream()
                .filter(liveTiming -> liveTiming.getNumber() == number)
                .collect(toList());
        return aggregate(race, filteredTimings);
    }

    private static DetailedDriverDTO aggregate(Race race, Collection<LiveTiming> filteredTimings) {
        DetailedDriverDTO dto = createInitialDto(race);
        Collection<RoundDTO> performedLaps = RoundAggregator.aggregate(filteredTimings);
        dto.setRounds(performedLaps);
        dto = aggregateDriverData(dto, filteredTimings, performedLaps);
        return dto;
    }

    /**
     * Aggregates all of the data of a driver based on his collection of timings.
     *
     * @param timingsPerDriver the timings for the driver.
     * @return the aggregated values for the driver.
     */
    private static DetailedDriverDTO aggregateDriverData(DetailedDriverDTO driver, Collection<LiveTiming> timingsPerDriver, Collection<RoundDTO> performedLaps) {
        if (!timingsPerDriver.isEmpty()) {
            LiveTiming firstTiming = timingsPerDriver.stream().findFirst().get();
            driver = setDriverInformation(driver, firstTiming);
        }

        if (!performedLaps.isEmpty()) {
            driver = setPerformanceInformation(driver, performedLaps);
        }

        return driver;
    }

    /**
     * Sets driver information in the DTO based on the first entry of the livetiming.
     *
     * @param driver     the driver dto.
     * @param liveTiming the first found live timing.
     * @return the dto with the values assigned.
     */
    private static DetailedDriverDTO setDriverInformation(DetailedDriverDTO driver, LiveTiming liveTiming) {
        driver.setName(liveTiming.getName());
        driver.setNumber(liveTiming.getNumber());
        driver.setCar(liveTiming.getCar());

        return driver;
    }

    /**
     * Loops over the performed round by a driver and calculates some metrics during one loop:
     * - best lap
     * - best sector one time
     * - best sector two time
     * - best sector three time
     * - amount of pit stops
     * <p>
     * Based on the last round, we can also set the following metrics:
     * - last position
     * - last state
     *
     * @param driver        the driver data we are aggregating.
     * @param performedLaps the laps performed by the driver.
     * @return the aggregated driver dto with additional metrics.
     */
    private static DetailedDriverDTO setPerformanceInformation(DetailedDriverDTO driver, Collection<RoundDTO> performedLaps) {
        long bestLap = -1L;
        long bestSectorOne = -1L;
        long bestSectorTwo = -1L;
        long bestSectorThree = -1L;
        int pitsStops = 0;
        int roundsDone = 0;

        if (!performedLaps.isEmpty()) {
            RoundDTO lastRound = Iterables.getLast(performedLaps);
            driver.setLastPosition(lastRound.getPosition());
            driver.setLastState(lastRound.getState());

            for (RoundDTO round : performedLaps) {

                roundsDone++;
                if (round.getLapTime() < bestLap || bestLap == -1L) {
                    bestLap = round.getLapTime();
                }

                if (round.isInPit()) {
                    pitsStops++;
                }

                if (round.getSectorOneTime() < bestSectorOne || bestSectorOne == -1L) {
                    bestSectorOne = round.getSectorOneTime();
                }

                if (round.getSectorTwoTime() < bestSectorTwo || bestSectorTwo == -1L) {
                    bestSectorTwo = round.getSectorTwoTime();
                }

                if (round.getSectorThreeTime() < bestSectorThree || bestSectorThree == -1L) {
                    bestSectorThree = round.getSectorThreeTime();
                }
            }
        }

        driver.setAmountOfRounds(roundsDone);
        driver.setPitStops(pitsStops);
        driver.setBestLap(bestLap);
        driver.setBestSectorOne(bestSectorOne);
        driver.setBestSectorTwo(bestSectorTwo);
        driver.setBestSectorThree(bestSectorThree);

        return driver;
    }

    private static DetailedDriverDTO createInitialDto(Race race) {

        return DetailedDriverDTO.builder()
                .raceId(race.getId())
                .amountOfRounds(0)
                .bestLap(-1L)
                .bestSectorOne(-1L)
                .bestSectorTwo(-1L)
                .bestSectorThree(-1L)
                .lastPosition(-1)
                .lastState(LiveTimingState.UNKNOWN)
                .pitStops(0)
                .build();
    }
}
