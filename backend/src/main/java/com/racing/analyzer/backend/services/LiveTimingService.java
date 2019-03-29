package com.racing.analyzer.backend.services;

import com.racing.analyzer.backend.dto.livetiming.LiveTimingDTO;
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

    @Autowired
    private LiveTimingMapper mapper;

    @Transactional
    public Optional<LiveTimingDTO> getById(long id) {
        return repository.findById(id)
                .map(r -> mapper.toDto(r));
    }

    @Transactional
    public Collection<LiveTimingDTO> getAll() {
        return repository.findAll().stream()
                .map(r -> mapper.toDto(r))
                .collect(toList());
    }

}
