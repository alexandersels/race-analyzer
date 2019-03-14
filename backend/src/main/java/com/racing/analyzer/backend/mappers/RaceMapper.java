package com.racing.analyzer.backend.mappers;

import com.racing.analyzer.backend.dto.RaceDTO;
import com.racing.analyzer.backend.entities.Race;

import static com.google.common.base.Preconditions.checkNotNull;

public class RaceMapper {

    public static RaceDTO toDto(Race race) {
        checkNotNull(race);

        final RaceDTO dto = new RaceDTO();
        dto.id = race.getId();
        dto.name = race.getName();
        dto.recording = race.isRecording();
        dto.url = race.getUrl();

        return dto;
    }
}
