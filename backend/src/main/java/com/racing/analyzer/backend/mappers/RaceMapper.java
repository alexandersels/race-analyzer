package com.racing.analyzer.backend.mappers;

import com.racing.analyzer.backend.dto.RaceDTO;
import com.racing.analyzer.backend.entities.Race;

import static com.google.common.base.Preconditions.checkNotNull;

public class RaceMapper {

    public static RaceDTO toDto(Race race) {
        checkNotNull(race);

        return RaceDTO.builder()
                .id(race.getId())
                .name(race.getName())
                .recording(race.isRecording())
                .url(race.getUrl())
                .build();
    }
}
