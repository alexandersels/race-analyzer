package com.racing.analyzer.backend.services.impl;

import com.racing.analyzer.backend.dto.statistics.DetailedDriverDTO;
import com.racing.analyzer.backend.exceptions.RaceEntityNotFoundException;
import com.racing.analyzer.backend.logic.aggregators.DetailedDriverAggregator;
import com.racing.analyzer.backend.repositories.RaceRepository;
import com.racing.analyzer.backend.services.DetailedDriverService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import javax.transaction.Transactional;

@Service
public class DetailedDriverServiceImpl implements DetailedDriverService {

    @Autowired
    private RaceRepository raceRepository;

    @Override
    @Transactional
    public Collection<DetailedDriverDTO> getDetailedDriverList(long raceId) {
        return raceRepository.findById(raceId)
                             .map(DetailedDriverAggregator::aggregateDetailedInfo)
                             .orElseThrow(RaceEntityNotFoundException::new);
    }

    @Override
    @Transactional
    public DetailedDriverDTO getDetailedDriver(long raceId, int driverNumber) {
        return raceRepository.findById(raceId)
                             .map(race -> DetailedDriverAggregator.aggregateDetailedDriverInfo(race, driverNumber))
                             .orElseThrow(RaceEntityNotFoundException::new);
    }
}
