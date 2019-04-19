package com.racing.analyzer.backend.services;

import com.racing.analyzer.backend.dto.race.CreateRaceDTO;
import com.racing.analyzer.backend.dto.race.RaceDTO;
import com.racing.analyzer.backend.dto.race.UpdateRaceDTO;

import java.util.Collection;

public interface RaceService {
    RaceDTO getById(long raceId);

    Collection<RaceDTO> getAll();

    RaceDTO update(UpdateRaceDTO updateRaceDto);

    RaceDTO changeRecordingState(long raceId, boolean isRecording);

    RaceDTO create(CreateRaceDTO createRaceDto);

    void delete(long id);

}
