package com.racing.analyzer.backend.services;

import com.racing.analyzer.backend.dto.livetiming.LiveTimingDTO;
import com.racing.analyzer.backend.entities.LiveTiming;
import com.racing.analyzer.backend.entities.Race;
import com.racing.analyzer.backend.mappers.LiveTimingMapper;
import com.racing.analyzer.backend.repositories.LiveTimingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Collection;
import java.util.Optional;

import static java.util.stream.Collectors.toList;

@Service
public class LiveTimingService {

    @Autowired
    private LiveTimingRepository repository;

    @Transactional
    public Optional<LiveTiming> getById(long id) {
        return repository.findById(id);
    }

    @Transactional
    public Collection<LiveTiming> getAll() {
        return repository.findAll();
    }

    @Transactional
    public Collection<LiveTiming> getTimingsForDriver(Race race, int number) {
        return race.getTimings().stream()
                .filter(timing -> timing.getNumber() == number)
                .collect(toList());
    }

}
