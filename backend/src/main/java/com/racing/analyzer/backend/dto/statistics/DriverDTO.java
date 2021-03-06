package com.racing.analyzer.backend.dto.statistics;

import com.racing.analyzer.backend.enums.LiveTimingState;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DriverDTO {

    private long raceId;
    private String name;
    private int pitStops;
    private int lastPosition;
    private int number;
    private long bestLap;
    private long amountOfRounds;
    private LiveTimingState lastState;

}
