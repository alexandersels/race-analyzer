package com.racing.analyzer.backend.services;

import com.racing.analyzer.backend.ScheduledTask;
import com.racing.analyzer.backend.dto.race.CreateRaceDTO;
import com.racing.analyzer.backend.dto.race.RaceDTO;
import com.racing.analyzer.backend.dto.race.UpdateRaceDTO;
import com.racing.analyzer.backend.entities.Race;
import com.racing.analyzer.backend.mappers.RaceMapper;
import com.racing.analyzer.backend.repositories.RaceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Optional;
import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;

import static com.google.common.base.Preconditions.checkNotNull;
import static java.util.stream.Collectors.toList;

@Service
public class RaceService {

    @Autowired
    private RaceRepository repository;

    @Autowired
    private ScheduledTask task;

    @Autowired
    private RaceMapper mapper;

    @Transactional
    public RaceDTO getById(long id) {
        return repository.findById(id).map(race -> mapper.toDto(race))
                         .orElseThrow(EntityNotFoundException::new);
    }

    @Transactional
    public Collection<RaceDTO> getAll() {
        return repository.findAll().stream()
                         .map(race -> mapper.toDto(race))
                         .collect(toList());
    }

    @Transactional
    public Optional<RaceDTO> getRaceJoinedWithTimings(long id) {
        return repository.getRaceJoinedWithTimings(id).map(race -> mapper.toDto(race));
    }

    @Transactional
    public Optional<Race> update(UpdateRaceDTO updateRaceDto) {
        checkNotNull(updateRaceDto);
        Optional<Race> race = repository.findById(updateRaceDto.getId());
        if (!race.isPresent()) {
            return Optional.empty();
        } else {
            Race updated = update(race.get(), updateRaceDto);
            return Optional.of(repository.save(updated));
        }
    }

    @Transactional
    public Optional<Race> changeRecordingState(long id, boolean isRecording) {
        Optional<Race> race = repository.findById(id);
        if (!race.isPresent()) {
            return Optional.empty();
        } else {
            Race toUpdate = race.get();
            toUpdate.setRecording(isRecording);
            repository.save(toUpdate);
            task.record(toUpdate);
            return Optional.of(toUpdate);
        }
    }

    @Transactional
    public Optional<Race> create(CreateRaceDTO createRaceDto) {
        checkNotNull(createRaceDto);
        Race toCreate = Race.builder()
                            .name(createRaceDto.getName())
                            .url(createRaceDto.getUrl())
                            .recording(false)
                            .build();
        return Optional.ofNullable(repository.save(toCreate));
    }

    @Transactional
    public void delete(long id) {
        repository.deleteById(id);
    }

    private Race update(Race race, UpdateRaceDTO updateRaceDTO) {
        if (!race.getName().equals(updateRaceDTO.getName())) {
            race.setName(updateRaceDTO.getName());
        }

        if (!race.getUrl().equals(updateRaceDTO.getUrl())) {
            race.setUrl(updateRaceDTO.getUrl());
        }
        return race;

    }

}
