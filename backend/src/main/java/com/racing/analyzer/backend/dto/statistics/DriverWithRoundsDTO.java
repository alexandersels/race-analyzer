package com.racing.analyzer.backend.dto.statistics;

import com.racing.analyzer.backend.enums.LiveTimingState;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.Collection;

@Data
@SuperBuilder
@NoArgsConstructor
public class DriverWithRoundsDTO extends DriverDTO {

    private Collection<RoundDTO> rounds;

    public DriverWithRoundsDTO(int number, String name, String car, int pitStops, long bestLap,
                               long bestSectorOne, long bestSectorTwo, long bestSectorThree, int lastPosition,
                               LiveTimingState lastState, long amountOfRounds, Collection<RoundDTO> rounds) {

        super(number, name, car, pitStops, bestLap, bestSectorOne, bestSectorTwo, bestSectorThree, lastPosition,
                lastState, amountOfRounds);

        this.rounds = rounds;
    }
}
