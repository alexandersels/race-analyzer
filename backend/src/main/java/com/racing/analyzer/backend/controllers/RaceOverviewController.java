package com.racing.analyzer.backend.controllers;

import com.racing.analyzer.backend.assemblers.RaceOverviewAssembler;
import com.racing.analyzer.backend.dto.statistics.RaceOverviewDTO;
import com.racing.analyzer.backend.services.RaceOverviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RaceOverviewController {

    @Autowired
    private RaceOverviewService raceOverviewService;

    @Autowired
    private RaceOverviewAssembler raceOverviewAssembler;

    @GetMapping("/races/{id}/overview")
    public ResponseEntity<?> getRaceOverview(@PathVariable long id) {
        RaceOverviewDTO dto = raceOverviewService.getRaceOverview(id);
        return ResponseEntity.ok().body(raceOverviewAssembler.toResource(dto));

    }
}
