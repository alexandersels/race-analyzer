package com.racing.analyzer.backend.services;

import com.racing.analyzer.backend.commands.CreateRaceCommand;
import com.racing.analyzer.backend.commands.UpdateRaceCommand;
import com.racing.analyzer.backend.entities.Race;
import com.racing.analyzer.backend.repositories.RaceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class RaceService {

    @Autowired
    private RaceRepository repository;

    @Transactional
    public Optional<Race> getById(Long id) {
        return repository.findById(id);
    }

    @Transactional
    public Collection<Race> getAll() {
        return repository.findAll();
    }

    @Transactional
    public Race update(Race race) {
        if (race == null) {
            throw new NullPointerException("race");
        }
        return repository.save(race);
    }

    @Transactional
    public Race create(CreateRaceCommand command) {
        if (command == null) {
            throw new NullPointerException("command");
        }
        return repository.save(command.getEntityToCreate());
    }

    @Transactional
    public void delete(Long id) {
        repository.deleteById(id);
    }

    @Transactional
    public void stopAllRecordings() {
        List<Race> updatedRaces = repository.findAll().stream()
                .filter(r -> r.isRecording())
                .map(race -> {
                    UpdateRaceCommand raceCommand = UpdateRaceCommand.getBuilder()
                            .startFrom(race)
                            .isRecording(false)
                            .build();
                    race.execute(raceCommand);
                    return race;
                })
                .collect(Collectors.toList());
        repository.saveAll(updatedRaces);
    }

}
