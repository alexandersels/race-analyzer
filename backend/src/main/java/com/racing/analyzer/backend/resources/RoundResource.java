package com.racing.analyzer.backend.resources;

import com.racing.analyzer.backend.dto.statistics.RoundDTO;
import com.racing.analyzer.backend.enums.LiveTimingState;
import lombok.Getter;
import org.springframework.hateoas.ResourceSupport;
import org.springframework.hateoas.core.Relation;

@Getter
@Relation(collectionRelation = "rounds")
public class RoundResource extends ResourceSupport {

    private final long lapTime;
    private final boolean inPit;
    private final long sectorOneTime;
    private final long sectorTwoTime;
    private final long sectorThreeTime;
    private final int position;
    private final LiveTimingState state;

    private RoundResource(RoundDTO dto) {
        lapTime = dto.getLapTime();
        inPit = dto.isInPit();
        sectorOneTime = dto.getSectorOneTime();
        sectorTwoTime = dto.getSectorTwoTime();
        sectorThreeTime = dto.getSectorThreeTime();
        position = dto.getPosition();
        state = dto.getState();
    }

    public static RoundResource fromDto(RoundDTO dto) {
        if(dto == null) {
            return null;
        }
        return new RoundResource(dto);
    }
}
