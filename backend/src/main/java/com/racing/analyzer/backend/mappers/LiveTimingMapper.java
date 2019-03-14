package com.racing.analyzer.backend.mappers;

import com.racing.analyzer.backend.dto.LiveTimingDTO;
import com.racing.analyzer.backend.entities.LiveTiming;

import static com.google.common.base.Preconditions.checkNotNull;

public class LiveTimingMapper {

    public static LiveTimingDTO toDto(LiveTiming liveTiming) {
        checkNotNull(liveTiming);

        final LiveTimingDTO dto = new LiveTimingDTO();
        dto.id = liveTiming.getId();
        dto.car = liveTiming.getCar();
        dto.carNumber = liveTiming.getNumber();
        dto.driverName = liveTiming.getName();
        dto.nationality = liveTiming.getNationality();
        dto.position = liveTiming.getPosition();
        dto.state = liveTiming.getState();
        dto.cls = liveTiming.getCls();
        dto.inPit = liveTiming.isInPit();
        dto.lastTime = liveTiming.getLastTime();
        dto.bestTime = liveTiming.getBestTime();
        dto.sectorOneTime = liveTiming.getSectorOne();
        dto.sectorTwoTime = liveTiming.getSectorTwo();
        dto.sectorThreeTime = liveTiming.getSectorThree();

        // TODO: double check that we don't perform an additional call when mapping this id.
        dto.race = liveTiming.getRace().getId();

        return dto;
    }

}
