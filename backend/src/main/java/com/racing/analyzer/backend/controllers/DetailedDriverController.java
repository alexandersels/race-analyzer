package com.racing.analyzer.backend.controllers;

import com.racing.analyzer.backend.assemblers.DetailedDriverAssembler;
import com.racing.analyzer.backend.dto.statistics.DetailedDriverDTO;
import com.racing.analyzer.backend.entities.Race;
import com.racing.analyzer.backend.resources.DetailedDriverResource;
import com.racing.analyzer.backend.services.DetailedDriverService;
import com.racing.analyzer.backend.services.RaceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Resources;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;
import java.util.Optional;

import static java.util.stream.Collectors.toList;

@RestController
public class DetailedDriverController {

    @Autowired
    private DetailedDriverService detailedDriverService;

    @Autowired
    private RaceService raceService;

    @Autowired
    private DetailedDriverAssembler detailedDriverAssembler;

    @GetMapping("/races/{id}/detailedDriverData")
    public ResponseEntity<?> getDetailedDriverList(@PathVariable long id) {
        Optional<Race> race = raceService.getById(id);
        if (!race.isPresent()) {
            return ResponseEntity.notFound().build();
        } else {
            Collection<DetailedDriverResource> detailedDriverResources = detailedDriverService
                    .getDetailedDriverList(race.get())
                    .stream()
                    .map(dto -> detailedDriverAssembler.toResource(dto))
                    .collect(toList());
            Resources resources = new Resources(detailedDriverResources);
            return ResponseEntity.ok().body(resources);
        }
    }

    @GetMapping("/races/{id}/detailedDriverData/{number}")
    public ResponseEntity<?> getDetailedDriver(@PathVariable long id, @PathVariable int number) {
        Optional<Race> race = raceService.getById(id);
        if (!race.isPresent()) {
            return ResponseEntity.notFound().build();
        } else {
            DetailedDriverDTO detailedDriverDto = detailedDriverService.getDetailedDriver(race.get(), number);
            return ResponseEntity.ok().body(detailedDriverAssembler.toResource(detailedDriverDto));
        }
    }

}