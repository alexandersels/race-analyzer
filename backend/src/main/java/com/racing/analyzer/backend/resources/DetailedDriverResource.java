package com.racing.analyzer.backend.resources;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.racing.analyzer.backend.assemblers.RoundAssembler;
import com.racing.analyzer.backend.dto.statistics.DetailedDriverDTO;
import com.racing.analyzer.backend.enums.LiveTimingState;
import lombok.Getter;
import org.springframework.hateoas.ResourceSupport;

import java.util.Collection;
import java.util.stream.Collectors;

@Getter
public class DetailedDriverResource extends ResourceSupport {

    @JsonIgnore
    private final RoundAssembler assembler;

    private final long raceId;
    private final int number;
    private final String name;
    private final String car;
    private final int pitStops;
    private final int lastPosition;
    private final LiveTimingState lastState;
    private final long bestLap;
    private final long bestSectorOne;
    private final long bestSectorTwo;
    private final long bestSectorThree;
    private final long amountOfRounds;
    private final Collection<RoundResource> rounds;

    private DetailedDriverResource(DetailedDriverDTO dto) {
        assembler = new RoundAssembler();

        this.raceId = dto.getRaceId();
        this.number = dto.getNumber();
        this.name = dto.getName();
        this.car = dto.getCar();
        this.pitStops = dto.getPitStops();
        this.lastPosition = dto.getLastPosition();
        this.lastState = dto.getLastState();
        this.bestLap = dto.getBestLap();
        this.bestSectorOne = dto.getBestSectorOne();
        this.bestSectorTwo = dto.getBestSectorTwo();
        this.bestSectorThree = dto.getBestSectorThree();
        this.amountOfRounds = dto.getAmountOfRounds();
        this.rounds = dto.getRounds().stream()
                .map(roundDTO -> assembler.toResource(roundDTO))
                .collect(Collectors.toList());
    }

    public static DetailedDriverResource fromDto(DetailedDriverDTO dto) {
        if (dto == null) {
            return null;
        }

        return new DetailedDriverResource(dto);
    }
}
