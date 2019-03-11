package com.racing.analyzer.backend.mappers;

import com.racing.analyzer.backend.dto.RaceDTO;
import com.racing.analyzer.backend.entities.Race;

public class RaceMapper {

    public static RaceDTO toDto(Race race) {
        final RaceDTO dto = new RaceDTO();
        dto.id = race.getId();
        dto.name = race.getName();
        dto.recording = race.isRecording();

        return dto;
    }
}
