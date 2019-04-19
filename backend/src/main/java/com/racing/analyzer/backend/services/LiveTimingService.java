package com.racing.analyzer.backend.services;

import com.racing.analyzer.backend.dto.livetiming.LiveTimingDTO;

import java.util.Collection;

public interface LiveTimingService {
    LiveTimingDTO getById(long liveTimingId);

    Collection<LiveTimingDTO> getAll();

    Collection<LiveTimingDTO> getTimingsForDriver(long raceId, int number);

    Collection<LiveTimingDTO> getTimingsForRace(long raceId);
}
