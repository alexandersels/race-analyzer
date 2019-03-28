package com.racing.analyzer.backend;

import com.racing.analyzer.backend.entities.LiveTiming;
import com.racing.analyzer.backend.entities.Race;
import com.racing.analyzer.backend.enums.LiveTimingState;

public class LiveTimingHelper {

    public static LiveTiming create(long id, String driverName, int number, String cls, int position, long lastTime, long bestTime,
                                    String nationality, boolean inPit, String car, LiveTimingState state, long sectorOne, long sectorTwo,
                                    long sectorThree, Race race) {
        return LiveTiming.builder()
                         .id(id)
                         .name(driverName)
                         .number(number)
                         .cls(cls)
                         .position(position)
                         .lastTime(lastTime)
                         .bestTime(bestTime)
                         .nationality(nationality)
                         .inPit(inPit)
                         .car(car)
                         .state(state)
                         .sectorOne(sectorOne)
                         .sectorTwo(sectorTwo)
                         .sectorThree(sectorThree)
                         .race(race)
                         .build();
    }
}
