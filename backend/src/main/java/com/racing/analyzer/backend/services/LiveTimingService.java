package com.racing.analyzer.backend.services;

import com.google.common.collect.ImmutableList;
import com.racing.analyzer.backend.dto.livetiming.LiveTimingDTO;
import com.racing.analyzer.backend.entities.LiveTiming;
import com.racing.analyzer.backend.mappers.LiveTimingMapper;
import com.racing.analyzer.backend.repositories.LiveTimingRepository;
import com.racing.analyzer.backend.repositories.RaceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Optional;
import javax.transaction.Transactional;

import static java.util.stream.Collectors.toList;

@Service
public class LiveTimingService {

    @Autowired
    private LiveTimingRepository liveTimingRepository;

    @Autowired
    private RaceRepository raceRepository;

    @Autowired
    private LiveTimingMapper liveTimingMapper;

    @Transactional
    public Optional<LiveTiming> getById(long id) {
        return liveTimingRepository.findById(id);
    }

    @Transactional
    public Collection<LiveTiming> getAll() {
        return liveTimingRepository.findAll();
    }

    @Transactional
    public Collection<LiveTimingDTO> getTimingsForDriver(long raceId, int number) {
        return raceRepository.findById(raceId)
                             .map(race -> race.getTimings().stream()
                                              .filter(timing -> timing.getNumber() == number)
                                              .map(x -> liveTimingMapper.toDto(x))
                                              .collect(toList()))
                             .orElse(ImmutableList.of());
    }

    @Transactional
    public Collection<LiveTimingDTO> getTimingsForRace(long raceId) {
        return raceRepository.findById(raceId)
                             .map(race -> race.getTimings().stream()
                                              .map(timing -> liveTimingMapper.toDto(timing))
                                              .collect(toList()))
                             .orElse(ImmutableList.of());
    }
}
