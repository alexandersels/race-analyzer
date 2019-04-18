package com.racing.analyzer.backend.services;

import com.racing.analyzer.backend.dto.livetiming.LiveTimingDTO;
import com.racing.analyzer.backend.exceptions.LiveTimingNotFoundException;
import com.racing.analyzer.backend.exceptions.RaceEntityNotFoundException;
import com.racing.analyzer.backend.mappers.LiveTimingMapper;
import com.racing.analyzer.backend.repositories.LiveTimingRepository;
import com.racing.analyzer.backend.repositories.RaceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Collection;

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
    public LiveTimingDTO getById(long liveTimingId) {
        return liveTimingRepository.findById(liveTimingId).map(liveTiming -> liveTimingMapper.toDto(liveTiming))
                .orElseThrow(LiveTimingNotFoundException::new);
    }

    @Transactional
    public Collection<LiveTimingDTO> getAll() {
        return liveTimingRepository.findAll().stream()
                .map(liveTiming -> liveTimingMapper.toDto(liveTiming))
                .collect(toList());
    }

    @Transactional
    public Collection<LiveTimingDTO> getTimingsForDriver(long raceId, int number) {
        return raceRepository.findById(raceId)
                .map(race -> race.getTimings().stream()
                        .filter(timing -> timing.getNumber() == number)
                        .map(liveTiming -> liveTimingMapper.toDto(liveTiming))
                        .collect(toList()))
                .orElseThrow(RaceEntityNotFoundException::new);
    }

    @Transactional
    public Collection<LiveTimingDTO> getTimingsForRace(long raceId) {
        return raceRepository.findById(raceId)
                .map(race -> race.getTimings().stream()
                        .map(timing -> liveTimingMapper.toDto(timing))
                        .collect(toList()))
                .orElseThrow(RaceEntityNotFoundException::new);
    }
}
