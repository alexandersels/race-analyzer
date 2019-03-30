package com.racing.analyzer.backend.services;

import com.racing.analyzer.backend.ScheduledTask;
import com.racing.analyzer.backend.commands.race.CreateRaceCommand;
import com.racing.analyzer.backend.commands.race.UpdateRaceCommand;
import com.racing.analyzer.backend.dto.race.RaceDTO;
import com.racing.analyzer.backend.dto.statistics.DriverWithRoundsDTO;
import com.racing.analyzer.backend.dto.statistics.RaceOverviewDTO;
import com.racing.analyzer.backend.entities.LiveTiming;
import com.racing.analyzer.backend.entities.Race;
import com.racing.analyzer.backend.logic.aggregators.DriverAggregator;
import com.racing.analyzer.backend.logic.aggregators.RaceOverviewAggregator;
import com.racing.analyzer.backend.mappers.RaceMapper;
import com.racing.analyzer.backend.repositories.RaceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static com.google.common.base.Preconditions.checkNotNull;
import static java.util.stream.Collectors.toList;

@Service
public class RaceService {

    @Autowired
    private RaceRepository repository;

    @Autowired
    private RaceMapper mapper;

    @Autowired
    private ScheduledTask task;

    @Transactional
    public Optional<RaceDTO> getById(long id) {
        return repository.findById(id)
                .map(r -> mapper.toDto(r));
    }

    @Transactional
    public Collection<RaceDTO> getAll() {
        return repository.findAll().stream()
                .map(r -> mapper.toDto(r))
                .collect(toList());
    }

    @Transactional
    public Optional<RaceOverviewDTO> getAggregatedRaceData(long id) {
        return repository.findById(id)
                .map(RaceOverviewAggregator::aggregate);
    }

    @Transactional
    public Collection<DriverWithRoundsDTO> getAggregatedDriverData(long id) {
        Optional<Race> race = repository.findById(id);
        return race.map(Race::getTimings)
                .map(DriverAggregator::aggregateWithRounds)
                .orElse(Collections.emptyList());
    }

    @Transactional
    public Optional<DriverWithRoundsDTO> getAggregatedDriverData(long id, int number) {
        Optional<Race> race = repository.findById(id);
        if (race.isPresent()) {

            List<LiveTiming> filteredTimings = race.get().getTimings().stream()
                    .filter(timing -> timing.getNumber() == number)
                    .collect(toList());
            return Optional.ofNullable(DriverAggregator.aggregateDriverWithRounds(filteredTimings));
        } else {
            return Optional.empty();
        }
    }
    
    @Transactional
    public Optional<RaceDTO> update(UpdateRaceCommand updateRaceCommand) {
        checkNotNull(updateRaceCommand);
        Optional<Race> entity = repository.findById(updateRaceCommand.getId());
        if (!entity.isPresent()) {
            return Optional.empty();
        } else {
            entity.get().execute(updateRaceCommand);
            Race updatedRace = repository.save(entity.get());
            updateTask(updatedRace);
            return Optional.of(mapper.toDto(updatedRace));
        }
    }

    @Transactional
    public Optional<RaceDTO> create(CreateRaceCommand createRaceCommand) {
        checkNotNull(createRaceCommand);
        Race createdEntity = repository.save(createRaceCommand.getEntityToCreate());
        return Optional.of(mapper.toDto(createdEntity));
    }

    @Transactional
    public void delete(long id) {
        repository.deleteById(id);
    }

    private void updateTask(Race race) {
        task.record(race);
    }
}
