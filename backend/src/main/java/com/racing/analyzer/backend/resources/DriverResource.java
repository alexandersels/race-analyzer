package com.racing.analyzer.backend.resources;

import com.racing.analyzer.backend.dto.statistics.DriverDTO;
import com.racing.analyzer.backend.enums.LiveTimingState;
import lombok.Getter;
import org.springframework.hateoas.ResourceSupport;
import org.springframework.hateoas.core.Relation;

import static com.google.common.base.Preconditions.checkNotNull;

@Getter
@Relation(collectionRelation = "drivers")
public class DriverResource extends ResourceSupport {

    private final int number;
    private final String name;
    private final int pitStops;
    private final int lastPosition;
    private final LiveTimingState lastState;
    private final long bestLap;
    private final long amountOfRounds;

    private DriverResource(DriverDTO dto) {
        this.number = dto.getNumber();
        this.name = dto.getName();
        this.pitStops = dto.getPitStops();
        this.lastPosition = dto.getLastPosition();
        this.lastState = dto.getLastState();
        this.bestLap = dto.getBestLap();
        this.amountOfRounds = dto.getAmountOfRounds();
    }

    public static DriverResource fromDto(DriverDTO dto) {
        if(dto == null) {
            return null;
        }
        return new DriverResource(dto);
    }
}
