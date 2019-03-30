package com.racing.analyzer.backend.logic.aggregators;

import com.google.common.collect.Iterables;
import com.racing.analyzer.backend.dto.statistics.DriverDTO;
import com.racing.analyzer.backend.dto.statistics.DriverWithRoundsDTO;
import com.racing.analyzer.backend.dto.statistics.RoundDTO;
import com.racing.analyzer.backend.entities.LiveTiming;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class DriverAggregator {

    /**
     * Creates a collection of aggregated driver data based on a collection of live timing data.
     *
     * @param liveTimings a given collection of live timing data.
     * @return a collection of aggregated information driver data.
     */
    public static Collection<DriverDTO> aggregate(Collection<LiveTiming> liveTimings) {

        Map<Integer, List<LiveTiming>> timingsPerDriver = liveTimings.stream().
                collect(Collectors.groupingBy(LiveTiming::getNumber));

        return timingsPerDriver.entrySet().stream()
                .map(kv -> kv.getValue())
                .map(timings -> {
                    DriverDTO dto = new DriverDTO();
                    Collection<RoundDTO> performedLaps = RoundAggregator.aggregate(timings);
                    return aggregateDriverData(dto, timings, performedLaps);
                })
                .collect(Collectors.toList());
    }

    public static DriverWithRoundsDTO aggregateDriverWithRounds(Collection<LiveTiming> timingsPerDriver) {
        DriverWithRoundsDTO dto = new DriverWithRoundsDTO();
        Collection<RoundDTO> performedLaps = RoundAggregator.aggregate(timingsPerDriver);
        dto = aggregateDriverData(dto, timingsPerDriver, performedLaps);
        dto.setRounds(performedLaps);

        return dto;
    }

    /**
     * Creates a collection of aggregated driver data with rounds based on a collection of live timing data.
     *
     * @param liveTimings a given collection of live timing data.
     * @return a collection of aggregated information driver data.
     */
    public static Collection<DriverWithRoundsDTO> aggregateWithRounds(Collection<LiveTiming> liveTimings) {

        Map<Integer, List<LiveTiming>> timingsPerDriver = liveTimings.stream().
                collect(Collectors.groupingBy(LiveTiming::getNumber));

        return timingsPerDriver.values().stream()
                .map(DriverAggregator::aggregateDriverWithRounds)
                .collect(Collectors.toList());
    }

    /**
     * Aggregates all of the data of a driver based on his collection of timings.
     *
     * @param timingsPerDriver the timings for the driver.
     * @return the aggregated values for the driver.
     */
    private static <TDriverDTO extends DriverDTO> TDriverDTO aggregateDriverData(TDriverDTO driver, Collection<LiveTiming> timingsPerDriver, Collection<RoundDTO> performedLaps) {
        if (!timingsPerDriver.isEmpty()){
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
     * @param driver the driver dto.
     * @param liveTiming the first found live timing.
     * @param <TDriverDTO> the type of driver dto we are creating.
     * @return the dto with the values assigned.
     */
    private static <TDriverDTO extends DriverDTO> TDriverDTO setDriverInformation(TDriverDTO driver, LiveTiming liveTiming) {
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
     *
     * Based on the last round, we can also set the following metrics:
     *  - last position
     *  - last state
     *
     * @param driver        the driver data we are aggregating.
     * @param performedLaps the laps performed by the driver.
     * @return the aggregated driver dto with additional metrics.
     */
    private static <TDriverDTO extends DriverDTO> TDriverDTO setPerformanceInformation(TDriverDTO driver, Collection<RoundDTO> performedLaps) {
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
}
