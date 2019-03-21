package com.racing.analyzer.backend.logic;

import com.racing.analyzer.backend.dto.DriverDTO;
import com.racing.analyzer.backend.dto.RaceDTO;
import com.racing.analyzer.backend.dto.RoundDTO;
import com.racing.analyzer.backend.entities.LiveTiming;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class DriverParser {

    public static Collection<DriverDTO> createDriverData(Collection<LiveTiming> liveTimings) {

        Map<Integer, List<LiveTiming>> timingsPerDriver = liveTimings.stream().
                collect(Collectors.groupingBy(LiveTiming::getNumber));

        return timingsPerDriver.entrySet().stream()
                .map(kv -> createDriver(kv.getValue()))
                .collect(Collectors.toList());
    }

    private static DriverDTO createDriver(Collection<LiveTiming> timings) {
        return timings.stream().findFirst()
                .map(timing -> {
                    DriverDTO dto = new DriverDTO();
                    dto.number = timing.getNumber();
                    dto.car = timing.getCar();
                    dto.name = timing.getName();
                    dto.rounds = RoundParser.createRoundDataForSpecificDriver(timings);
                    calculateRoundBasedMetrics(dto);
                    return dto;
                }).orElse(null);
    }

    private static void calculateRoundBasedMetrics(DriverDTO driver) {
        long bestLap = -1L;
        int pitsStops = 0;
        for(RoundDTO round : driver.rounds) {
            if(round.lapTime < bestLap || bestLap == -1L) {
                bestLap = round.lapTime;
            }
            if(round.inPit) {
                pitsStops++;
            }
        }

        driver.pitStops = pitsStops;
        driver.bestLap = bestLap;
    }
}
