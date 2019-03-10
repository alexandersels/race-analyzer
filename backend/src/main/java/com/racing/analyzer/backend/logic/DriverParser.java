package com.racing.analyzer.backend.logic;

import com.racing.analyzer.backend.dto.DriverDTO;
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
                    dto.pitStops = dto.rounds.stream().mapToInt(r -> r.inPit ? 1 : 0).sum();
                    return dto;
                }).orElse(null);
    }
}
