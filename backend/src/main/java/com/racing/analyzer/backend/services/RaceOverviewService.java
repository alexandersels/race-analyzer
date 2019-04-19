package com.racing.analyzer.backend.services;

import com.racing.analyzer.backend.dto.statistics.RaceOverviewDTO;

import javax.transaction.Transactional;

public interface RaceOverviewService {
    RaceOverviewDTO getRaceOverview(long raceId);
}
