package com.racing.analyzer.backend.resources;

import com.racing.analyzer.backend.dto.livetiming.LiveTimingDTO;
import com.racing.analyzer.backend.enums.LiveTimingState;
import lombok.Getter;
import org.springframework.hateoas.ResourceSupport;
import org.springframework.hateoas.core.Relation;

import java.util.Optional;

import static com.google.common.base.Preconditions.checkNotNull;

@Getter
@Relation(collectionRelation = "livetimings")
public class LiveTimingResource extends ResourceSupport {

    private final String name;
    private final int number;
    private final String car;
    private final String cls;
    private final int position;
    private final LiveTimingState state;
    private final String nationality;
    private final boolean inPit;
    private final long lastTime;
    private final long bestTime;
    private final long sectorOne;
    private final long sectorTwo;
    private final long sectorThree;
    private final long raceId;

    private LiveTimingResource(LiveTimingDTO dto) {
        this.name = dto.getName();
        this.number = dto.getNumber();
        this.car = dto.getCar();
        this.cls = dto.getCls();
        this.position = dto.getPosition();
        this.state = dto.getState();
        this.nationality = dto.getNationality();
        this.inPit = dto.isInPit();
        this.lastTime = dto.getLastTime();
        this.bestTime = dto.getBestTime();
        this.sectorOne = dto.getSectorOne();
        this.sectorTwo = dto.getSectorTwo();
        this.sectorThree = dto.getSectorThree();
        this.raceId = dto.getRace();
    }

    public static Optional<LiveTimingResource> fromDto(LiveTimingDTO dto) {
        if (dto == null) {
            return Optional.empty();
        }
        return Optional.of(new LiveTimingResource(dto));
    }
}
