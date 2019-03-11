package com.racing.analyzer.backend.services;

import com.racing.analyzer.backend.commands.CreateRaceCommand;
import com.racing.analyzer.backend.entities.Race;
import com.racing.analyzer.backend.repositories.RaceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Collection;
import java.util.Optional;

@Service
public class RaceService {

    @Autowired
    private RaceRepository raceRepository;

    @Transactional
    public Optional<Race> getById(Long id) {
        return raceRepository.findById(id);
    }

    @Transactional
    public Collection<Race> getAll() {
        return raceRepository.findAll();
    }

    @Transactional
    public Race update(Race race) {
        if(race == null) {
            throw new NullPointerException("race");
        }
        return raceRepository.save(race);
    }

    @Transactional
    public Race create(CreateRaceCommand command) {
        if(command == null) {
            throw new NullPointerException("command");
        }
        return raceRepository.save(command.getEntityToCreate());
    }

    @Transactional
    public void delete(Long id) {
        raceRepository.deleteById(id);
    }

}
