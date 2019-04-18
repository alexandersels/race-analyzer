package com.racing.analyzer.backend.services;

import com.racing.analyzer.backend.dto.statistics.RaceOverviewDTO;
import com.racing.analyzer.backend.logic.aggregators.RaceOverviewAggregator;
import com.racing.analyzer.backend.repositories.RaceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class RaceOverviewService {

    @Autowired
    private RaceRepository raceRepository;

    @Transactional
    public RaceOverviewDTO getRaceOverview(long raceId) {
        return raceRepository.findById(raceId).map(RaceOverviewAggregator::aggregate).orElse(null);
    }

}
