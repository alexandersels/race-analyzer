package com.racing.analyzer.backend.services;

import com.racing.analyzer.backend.dto.statistics.DetailedDriverDTO;
import com.racing.analyzer.backend.exceptions.RaceEntityNotFoundException;
import com.racing.analyzer.backend.logic.aggregators.DetailedDriverAggregator;
import com.racing.analyzer.backend.repositories.RaceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Collection;

@Service
public class DetailedDriverService {

    @Autowired
    private RaceRepository raceRepository;

    @Transactional
    public Collection<DetailedDriverDTO> getDetailedDriverList(long raceId) {
        return raceRepository.findById(raceId)
                .map(DetailedDriverAggregator::aggregateDetailedInfo)
                .orElseThrow(RaceEntityNotFoundException::new);
    }

    @Transactional
    public DetailedDriverDTO getDetailedDriver(long raceId, int driverNumber) {
        return raceRepository.findById(raceId)
                .map(race -> DetailedDriverAggregator.aggregateDetailedDriverInfo(race, driverNumber))
                .orElseThrow(RaceEntityNotFoundException::new);
    }
}
