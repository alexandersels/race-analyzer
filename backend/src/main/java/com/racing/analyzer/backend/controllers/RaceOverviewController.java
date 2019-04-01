package com.racing.analyzer.backend.controllers;

import com.racing.analyzer.backend.assemblers.RaceOverviewAssembler;
import com.racing.analyzer.backend.dto.statistics.RaceOverviewDTO;
import com.racing.analyzer.backend.entities.Race;
import com.racing.analyzer.backend.mappers.LiveTimingMapper;
import com.racing.analyzer.backend.services.RaceOverviewService;
import com.racing.analyzer.backend.services.RaceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
public class RaceOverviewController {

    @Autowired
    private RaceService raceService;

    @Autowired
    private RaceOverviewService raceOverviewService;

    @Autowired
    private RaceOverviewAssembler assembler;

    @GetMapping("/races/{id}/overview")
    public ResponseEntity<?> getRaceOverview(@PathVariable long id) {
        Optional<Race> race = raceService.getRaceJoinedWithTimings(id);
        if (!race.isPresent()) {
            return ResponseEntity.notFound().build();
        } else {
            RaceOverviewDTO dto = raceOverviewService.getRaceOverview(race.get());
            return ResponseEntity.ok().body(assembler.toResource(dto));
        }
    }
}
