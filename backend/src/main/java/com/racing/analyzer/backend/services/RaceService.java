package com.racing.analyzer.backend.services;

import com.racing.analyzer.backend.ScheduledTask;
import com.racing.analyzer.backend.commands.CreateRaceCommand;
import com.racing.analyzer.backend.commands.UpdateRaceCommand;
import com.racing.analyzer.backend.dto.race.CreateRaceDTO;
import com.racing.analyzer.backend.dto.race.RaceDTO;
import com.racing.analyzer.backend.dto.race.UpdateRaceDTO;
import com.racing.analyzer.backend.entities.Race;
import com.racing.analyzer.backend.mappers.RaceMapper;
import com.racing.analyzer.backend.repositories.RaceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Collection;
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
