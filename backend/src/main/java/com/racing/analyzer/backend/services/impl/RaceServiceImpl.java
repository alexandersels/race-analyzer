package com.racing.analyzer.backend.services.impl;

import com.racing.analyzer.backend.dto.race.CreateRaceDTO;
import com.racing.analyzer.backend.dto.race.RaceDTO;
import com.racing.analyzer.backend.dto.race.UpdateRaceDTO;
import com.racing.analyzer.backend.entities.Race;
import com.racing.analyzer.backend.exceptions.RaceEntityNotFoundException;
import com.racing.analyzer.backend.mappers.RaceMapper;
import com.racing.analyzer.backend.repositories.RaceRepository;
import com.racing.analyzer.backend.scheduled.ScheduledTask;
import com.racing.analyzer.backend.services.RaceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;

import static com.google.common.base.Preconditions.checkNotNull;
import static java.util.stream.Collectors.toList;

@Service
public class RaceServiceImpl implements RaceService {

    @Autowired
    private RaceRepository raceRepository;

    @Autowired
    private ScheduledTask task;

    @Autowired
    private RaceMapper raceMapper;

    @Transactional
    public RaceDTO getById(long raceId) {
        return raceRepository.findById(raceId).map(race -> raceMapper.toDto(race))
                             .orElseThrow(EntityNotFoundException::new);
    }

    @Transactional
    public Collection<RaceDTO> getAll() {
        return raceRepository.findAll().stream()
                             .map(race -> raceMapper.toDto(race))
                             .collect(toList());
    }

    @Transactional
    public RaceDTO update(UpdateRaceDTO updateRaceDto) {
        checkNotNull(updateRaceDto);
        return raceRepository.findById(updateRaceDto.getId())
                             .map(race -> {
                                 race.update(updateRaceDto);
                                 return raceMapper.toDto(raceRepository.save(race));
                             })
                             .orElseThrow(RaceEntityNotFoundException::new);
    }

    @Transactional
    public RaceDTO changeRecordingState(long raceId, boolean isRecording) {
        return raceRepository.findById(raceId)
                             .map(race -> {
                                 race.setRecording(isRecording);
                                 if (isRecording) {
                                     task.startRecording(race);
                                 } else {
                                     task.stopRecording(race);
                                 }
                                 return raceMapper.toDto(raceRepository.save(race));
                             })
                             .orElseThrow(RaceEntityNotFoundException::new);
    }

    @Transactional
    public RaceDTO create(CreateRaceDTO createRaceDto) {
        checkNotNull(createRaceDto);
        Race toCreate = Race.builder()
                            .name(createRaceDto.getName())
                            .url(createRaceDto.getUrl())
                            .recording(false)
                            .build();
        return raceMapper.toDto(raceRepository.save(toCreate));
    }

    @Transactional
    public Collection<RaceDTO> getRecordingRaces() {
        return raceRepository.findAllRecordingRaces().stream()
                             .map(raceMapper::toDto)
                             .collect(toList());
    }

    @Transactional
    public void delete(long id) {
        raceRepository.deleteById(id);
    }


}
