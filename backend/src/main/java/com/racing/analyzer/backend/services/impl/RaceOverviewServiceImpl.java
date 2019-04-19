package com.racing.analyzer.backend.services.impl;

import com.racing.analyzer.backend.dto.statistics.RaceOverviewDTO;
import com.racing.analyzer.backend.logic.aggregators.RaceOverviewAggregator;
import com.racing.analyzer.backend.repositories.RaceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class RaceOverviewServiceImpl implements com.racing.analyzer.backend.services.RaceOverviewService {

    @Autowired
    private RaceRepository raceRepository;

    @Override
    @Transactional
    public RaceOverviewDTO getRaceOverview(long raceId) {
        return raceRepository.findById(raceId).map(RaceOverviewAggregator::aggregate).orElse(null);
    }

}
