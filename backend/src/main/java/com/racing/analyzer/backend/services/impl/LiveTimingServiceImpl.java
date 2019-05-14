package com.racing.analyzer.backend.services.impl;

import com.racing.analyzer.backend.dto.livetiming.LiveTimingDTO;
import com.racing.analyzer.backend.exceptions.LiveTimingNotFoundException;
import com.racing.analyzer.backend.exceptions.RaceEntityNotFoundException;
import com.racing.analyzer.backend.mappers.LiveTimingMapper;
import com.racing.analyzer.backend.repositories.LiveTimingRepository;
import com.racing.analyzer.backend.repositories.RaceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import javax.transaction.Transactional;

import static java.util.stream.Collectors.toList;

@Service
public class LiveTimingServiceImpl implements com.racing.analyzer.backend.services.LiveTimingService {

    @Autowired
    private LiveTimingMapper liveTimingMapper;

    @Autowired
    private LiveTimingRepository liveTimingRepository;

    @Autowired
    private RaceRepository raceRepository;

    @Override
    @Transactional
    public LiveTimingDTO getById(long liveTimingId) {
        return liveTimingRepository.findById(liveTimingId).map(liveTiming -> liveTimingMapper.toDto(liveTiming))
                                   .orElseThrow(LiveTimingNotFoundException::new);
    }

    @Override
    @Transactional
    public Collection<LiveTimingDTO> getAll() {
        return liveTimingRepository.findAll().stream()
                                   .map(liveTiming -> liveTimingMapper.toDto(liveTiming))
                                   .collect(toList());
    }

    @Override
    @Transactional
    public Collection<LiveTimingDTO> getTimingsForDriver(long raceId, int number) {
        return raceRepository.findById(raceId)
                             .map(race -> race.getTimings().stream()
                                              .filter(timing -> timing.getNumber() == number)
                                              .map(liveTiming -> liveTimingMapper.toDto(liveTiming))
                                              .collect(toList()))
                             .orElseThrow(RaceEntityNotFoundException::new);
    }

    @Override
    @Transactional
    public Collection<LiveTimingDTO> getTimingsForRace(long raceId) {
        return raceRepository.findById(raceId)
                             .map(race -> race.getTimings().stream()
                                              .map(timing -> liveTimingMapper.toDto(timing))
                                              .collect(toList()))
                             .orElseThrow(RaceEntityNotFoundException::new);
    }
}
