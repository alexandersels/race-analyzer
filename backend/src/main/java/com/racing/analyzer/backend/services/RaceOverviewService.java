package com.racing.analyzer.backend.services;

import com.racing.analyzer.backend.ScheduledTask;
import com.racing.analyzer.backend.commands.race.CreateRaceCommand;
import com.racing.analyzer.backend.commands.race.UpdateRaceCommand;
import com.racing.analyzer.backend.dto.statistics.DetailedDriverDTO;
import com.racing.analyzer.backend.dto.statistics.RaceOverviewDTO;
import com.racing.analyzer.backend.entities.LiveTiming;
import com.racing.analyzer.backend.entities.Race;
import com.racing.analyzer.backend.logic.aggregators.DetailedDriverAggregator;
import com.racing.analyzer.backend.logic.aggregators.RaceOverviewAggregator;
import com.racing.analyzer.backend.repositories.RaceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import static com.google.common.base.Preconditions.checkNotNull;
import static java.util.stream.Collectors.toList;

@Service
public class RaceOverviewService {

    @Autowired
    private RaceRepository repository;

    @Autowired
    private ScheduledTask task;

    @Transactional
    public RaceOverviewDTO getRaceOverview(Race race) {
        return RaceOverviewAggregator.aggregate(race);
    }

}
