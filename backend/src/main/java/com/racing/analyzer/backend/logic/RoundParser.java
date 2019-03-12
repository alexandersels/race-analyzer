package com.racing.analyzer.backend.logic;

import com.racing.analyzer.backend.dto.RoundDTO;
import com.racing.analyzer.backend.entities.LiveTiming;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class RoundParser {

    public static Collection<RoundDTO> createRoundDataForSpecificDriver(Collection<LiveTiming> timings) {

        List<RoundDTO> rounds = new ArrayList<>();

        LiveTiming previousTiming = null;
        for (LiveTiming liveTiming : timings) {

            if (liveTiming.isInPit()) {
                if (!previousTiming.isInPit()) {
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
        RoundDTO dto = new RoundDTO();
        dto.inPit = timing.isInPit();
        dto.lapTime = timing.getLastTime();
        dto.sectorOneTime = timing.getSectorOne();
        dto.sectorTwoTime = timing.getSectorTwo();
        dto.sectorThreeTime = timing.getSectorThree();

        return dto;
    }

    private static boolean sectorsAreDifferent(LiveTiming oldValue, LiveTiming newValue) {
        return (oldValue.getSectorOne() != newValue.getSectorOne()) ||
                (oldValue.getSectorTwo() != newValue.getSectorTwo()) ||
                (oldValue.getSectorThree() != newValue.getSectorThree());
    }

}
