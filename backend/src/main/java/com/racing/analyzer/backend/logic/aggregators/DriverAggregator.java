package com.racing.analyzer.backend.logic.aggregators;

import com.google.common.collect.Iterables;
import com.racing.analyzer.backend.dto.statistics.DriverDTO;
import com.racing.analyzer.backend.dto.statistics.RoundDTO;
import com.racing.analyzer.backend.entities.LiveTiming;
import com.racing.analyzer.backend.entities.Race;
import com.racing.analyzer.backend.enums.LiveTimingState;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.google.common.base.Preconditions.checkNotNull;

public class DriverAggregator {

    /**
     * Creates a collection of aggregated driver data based on a collection of live timing data.
     *
     * @param race the race to aggregateDetailedInfo.
     * @return a collection of aggregated information driver data.
     */
    public static Collection<DriverDTO> aggregate(Race race) {
        checkNotNull(race);
        Map<Integer, List<LiveTiming>> timingsPerDriver = race.getTimings().stream().
                collect(Collectors.groupingBy(LiveTiming::getNumber));

        return timingsPerDriver.entrySet().stream()
                .map(Map.Entry::getValue)
                .map(timings -> {
                    Collection<RoundDTO> performedLaps = RoundAggregator.aggregate(timings);
                    DriverDTO dto = createInitialDto(race);
                    return aggregateDriverData(dto, timings, performedLaps);
                })
                .collect(Collectors.toList());
    }

    /**
     * Aggregates all of the data of a driver based on his collection of timings.
     *
     * @param timingsPerDriver the timings for the driver.
     * @return the aggregated values for the driver.
     */
    private static DriverDTO aggregateDriverData(DriverDTO driver, Collection<LiveTiming> timingsPerDriver, Collection<RoundDTO> performedLaps) {
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
    private static DriverDTO setDriverInformation(DriverDTO driver, LiveTiming liveTiming) {
        driver.setName(liveTiming.getName());
        driver.setNumber(liveTiming.getNumber());
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
    private static DriverDTO setPerformanceInformation(DriverDTO driver, Collection<RoundDTO> performedLaps) {
        long bestLap = -1L;
        int pitsStops = 0;
        int roundsDone = 0;
        int position = -1;
        LiveTimingState state = LiveTimingState.UNKNOWN;

        if (!performedLaps.isEmpty()) {
            RoundDTO lastRound = Iterables.getLast(performedLaps);
            position = lastRound.getPosition();
            state = lastRound.getState();
            for (RoundDTO round : performedLaps) {
                roundsDone++;
                if (round.getLapTime() < bestLap || bestLap == -1L) {
                    bestLap = round.getLapTime();
                }

                if (round.isInPit()) {
                    pitsStops++;
                }
            }
        }

        driver.setAmountOfRounds(roundsDone);
        driver.setPitStops(pitsStops);
        driver.setBestLap(bestLap);
        driver.setLastPosition(position);
        driver.setLastState(state);

        return driver;
    }

    private static DriverDTO createInitialDto(Race race) {
        return DriverDTO.builder()
                .raceId(race.getId())
                .lastPosition(-1)
                .lastState(LiveTimingState.UNKNOWN)
                .amountOfRounds(0)
                .pitStops(0)
                .bestLap(-1L)
                .build();
    }
}
