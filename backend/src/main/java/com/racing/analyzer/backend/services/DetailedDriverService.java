package com.racing.analyzer.backend.services;

import com.racing.analyzer.backend.dto.statistics.DetailedDriverDTO;
import com.racing.analyzer.backend.entities.Race;
import com.racing.analyzer.backend.logic.aggregators.DetailedDriverAggregator;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Collection;

@Service
public class DetailedDriverService {

    @Transactional
    public Collection<DetailedDriverDTO> getDetailedDriverList(Race race) {
        return DetailedDriverAggregator.aggregateDetailedInfo(race);
    }

    @Transactional
    public DetailedDriverDTO getDetailedDriver(Race race, int number) {
        return DetailedDriverAggregator.aggregateDetailedDriverInfo(race, number);
    }

}
