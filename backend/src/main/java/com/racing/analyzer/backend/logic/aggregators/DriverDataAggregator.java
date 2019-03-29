package com.racing.analyzer.backend.logic.aggregators;

import com.google.common.collect.Iterables;
import com.racing.analyzer.backend.dto.statistics.AggregatedDriverDTO;
import com.racing.analyzer.backend.dto.statistics.AggregatedRoundDTO;
import com.racing.analyzer.backend.entities.LiveTiming;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

public class DriverDataAggregator {

    /**
     * Creates a collection of aggregation driver data based on a collection of live timing data.
     *
     * @param liveTimings a given collection of live timing data.
     * @return a collection of aggregated information driver data.
     */
    public static Collection<AggregatedDriverDTO> aggregate(Collection<LiveTiming> liveTimings) {

        Map<Integer, List<LiveTiming>> timingsPerDriver = liveTimings.stream().
                collect(Collectors.groupingBy(LiveTiming::getNumber));

        return timingsPerDriver.entrySet().stream()
                .map(kv -> aggregateDriverData(kv.getValue()))
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }

    /**
     * Aggregates all of the data of a driver based on his collection of timings.
     *
     * @param timingsPerDriver the timings for the driver.
     * @return the aggregated values for the driver.
     */
    private static AggregatedDriverDTO aggregateDriverData(Collection<LiveTiming> timingsPerDriver) {
        return timingsPerDriver.stream().findFirst()
                .map(timing -> {
                    Collection<AggregatedRoundDTO> performedLaps = RoundDataAggregator.aggregate(timingsPerDriver);
                    AggregatedRoundDTO lastRound = Iterables.getLast(performedLaps);

                    AggregatedDriverDTO dto = AggregatedDriverDTO.builder()
                            .number(timing.getNumber())
                            .car(timing.getCar())
                            .name(timing.getName())
                            .lastPosition(lastRound.getPosition())
                            .lastState(lastRound.getState())
                            .rounds(performedLaps)
                            .build();

                    return accumulateMetrics(dto, performedLaps);
                }).orElse(null);
    }

    /**
     * Loops over the performed round by a driver and calculates some metrics during one loop:
     *  - best lap
     *  - best sector one time
     *  - best sector two time
     *  - best sector three time
     *  - amount of pit stops
     *
     * @param driver the driver data we are aggregating.
     * @param performedLaps the laps performed by the driver.
     * @return the aggregated driver dto with additional metrics.
     */
    private static AggregatedDriverDTO accumulateMetrics(AggregatedDriverDTO driver,
                                                         Collection<AggregatedRoundDTO> performedLaps) {

        long bestLap = -1L;
        long bestSectorOne = -1L;
        long bestSectorTwo = -1L;
        long bestSectorThree = -1L;
        int pitsStops = 0;
        for (AggregatedRoundDTO round : performedLaps) {

            if (round.getLapTime() < bestLap || bestLap == -1L) {
                bestLap = round.getLapTime();
            }

            if (round.isInPit()) {
                pitsStops++;
            }

            if(round.getSectorOneTime() < bestSectorOne || bestSectorOne == -1L) {
                bestSectorOne = round.getSectorOneTime();
            }

            if(round.getSectorTwoTime() < bestSectorTwo || bestSectorTwo == -1L) {
                bestSectorTwo = round.getSectorTwoTime();
            }

            if(round.getSectorThreeTime() < bestSectorThree || bestSectorThree == -1L) {
                bestSectorThree = round.getSectorThreeTime();
            }
        }

        driver.setPitStops(pitsStops);
        driver.setBestLap(bestLap);
        driver.setBestSectorOne(bestSectorOne);
        driver.setBestSectorTwo(bestSectorTwo);
        driver.setBestSectorThree(bestSectorThree);

        return driver;
    }
}
