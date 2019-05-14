package com.racing.analyzer.backend.resources;

import com.fasterxml.jackson.annotation.JsonUnwrapped;
import com.racing.analyzer.backend.assemblers.RoundAssembler;
import com.racing.analyzer.backend.dto.statistics.DetailedDriverDTO;
import com.racing.analyzer.backend.enums.LiveTimingState;
import lombok.Getter;
import org.springframework.hateoas.ResourceSupport;
import org.springframework.hateoas.Resources;
import org.springframework.hateoas.core.EmbeddedWrapper;
import org.springframework.hateoas.core.EmbeddedWrappers;
import org.springframework.hateoas.core.Relation;

import java.util.List;
import java.util.stream.Collectors;

import static com.google.common.base.Preconditions.checkNotNull;

@Getter
@Relation(collectionRelation = "drivers")
public class DetailedDriverResource extends ResourceSupport {

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

    @JsonUnwrapped
    private final Resources rounds;

    private DetailedDriverResource(DetailedDriverDTO dto) {
        RoundAssembler assembler = new RoundAssembler();
        EmbeddedWrappers wrappers = new EmbeddedWrappers(false);

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
        List<EmbeddedWrapper> collect = dto.getRounds().stream()
                                           .map(roundDTO -> wrappers.wrap(assembler.toResource(roundDTO)))
                                           .collect(Collectors.toList());
        rounds = new Resources(collect);

    }

    public static DetailedDriverResource fromDto(DetailedDriverDTO dto) {
        if(dto == null) {
            return null;
        }
        return new DetailedDriverResource(dto);
    }
}
