package com.racing.analyzer.backend.dto.statistics;

import com.racing.analyzer.backend.enums.LiveTimingState;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.Collection;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DetailedDriverDTO {

    private long raceId;
    private int number;
    private String name;
    private String car;
    private int pitStops;
    private int lastPosition;
    private LiveTimingState lastState;
    private long bestLap;
    private long bestSectorOne;
    private long bestSectorTwo;
    private long bestSectorThree;
    private long amountOfRounds;
    private Collection<RoundDTO> rounds;

}
