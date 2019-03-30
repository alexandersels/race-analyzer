package com.racing.analyzer.backend.logic.aggregators;

import com.racing.analyzer.backend.dto.statistics.RoundDTO;
import com.racing.analyzer.backend.entities.LiveTiming;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class RoundAggregator {

    /**
     * Transforms the given live timing data for a specific driver into aggregated round dto objects.
     *
     * @param timings the given live timing data for a specific driver.
     * @return the mapped collection of round data for a specific driver.
     */
    public static Collection<RoundDTO> aggregate(Collection<LiveTiming> timings) {

        List<RoundDTO> rounds = new ArrayList<>();

        LiveTiming previousTiming = null;
        for (LiveTiming liveTiming : timings) {

            if (liveTiming.isInPit()) {
                if (previousTiming != null && !previousTiming.isInPit()) {
                    previousTiming = liveTiming;
                    rounds.add(createRoundData(liveTiming));
                }
            } else {
                if (liveTiming.areSectorsFilledIn() || (previousTiming != null && previousTiming.isInPit())) {
                    if (previousTiming == null) {
                        previousTiming = liveTiming;
                        rounds.add(createRoundData(liveTiming));
                    } else {
                        if ((previousTiming.getLastTime() != liveTiming.getLastTime()) || // the lap times are different
                                ((previousTiming.getLastTime() == liveTiming.getLastTime()) && sectorsAreDifferent(previousTiming, liveTiming))) { // lap times are identical but sectores times changed
                            previousTiming = liveTiming;
                            rounds.add(createRoundData(liveTiming));
                        }
                    }
                }
            }
        }

        return rounds;
    }

    private static RoundDTO createRoundData(LiveTiming timing) {
        return RoundDTO.builder()
                .inPit(timing.isInPit())
                .lapTime(timing.getLastTime())
                .sectorOneTime(timing.getSectorOne())
                .sectorTwoTime(timing.getSectorTwo())
                .sectorThreeTime(timing.getSectorThree())
                .position(timing.getPosition())
                .state(timing.getState())
                .build();
    }

    private static boolean sectorsAreDifferent(LiveTiming oldValue, LiveTiming newValue) {
        return (oldValue.getSectorOne() != newValue.getSectorOne()) ||
                (oldValue.getSectorTwo() != newValue.getSectorTwo()) ||
                (oldValue.getSectorThree() != newValue.getSectorThree());
    }

}
