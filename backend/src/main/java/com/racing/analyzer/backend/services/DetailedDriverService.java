package com.racing.analyzer.backend.services;

import com.racing.analyzer.backend.dto.statistics.DetailedDriverDTO;
import com.racing.analyzer.backend.logic.aggregators.DetailedDriverAggregator;
import com.racing.analyzer.backend.repositories.RaceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import javax.transaction.Transactional;

@Service
public class DetailedDriverService {

    @Autowired
    private RaceRepository raceRepository;

    @Transactional
    public Collection<DetailedDriverDTO> getDetailedDriverList(long raceId) {
        return raceRepository.findById(raceId)
                             .map(DetailedDriverAggregator::aggregateDetailedInfo)
                             .orElse(null);
    }

    @Transactional
    public DetailedDriverDTO getDetailedDriver(long raceId, int driverNumber) {
        return raceRepository.findById(raceId)
                             .map(race -> DetailedDriverAggregator.aggregateDetailedDriverInfo(race, driverNumber))
                             .orElse(null);
    }
}
