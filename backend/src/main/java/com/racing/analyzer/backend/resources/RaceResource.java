package com.racing.analyzer.backend.resources;

import com.racing.analyzer.backend.dto.race.RaceDTO;
import lombok.Getter;
import org.springframework.hateoas.ResourceSupport;
import org.springframework.hateoas.core.Relation;

import java.util.Optional;

@Getter
@Relation(collectionRelation = "races")
public class RaceResource extends ResourceSupport {

    private final long raceId;
    private final String name;
    private final boolean recording;
    private final String url;
    private final int version;

    private RaceResource(RaceDTO race) {
        this.raceId = race.getId();
        this.name = race.getName();
        this.recording = race.isRecording();
        this.url = race.getUrl();
        this.version = race.getVersion();
    }

    public static Optional<RaceResource> fromDto(RaceDTO dto) {
        if (dto == null) {
            return Optional.empty();
        }
        return Optional.of(new RaceResource(dto));
    }
}
