package com.racing.analyzer.backend.controllers.impl;

import com.racing.analyzer.backend.assemblers.RaceOverviewAssembler;
import com.racing.analyzer.backend.dto.statistics.RaceOverviewDTO;
import com.racing.analyzer.backend.services.RaceOverviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;

@Component
public class RaceOverviewControllerImpl implements com.racing.analyzer.backend.controllers.RaceOverviewController {

    @Autowired
    private RaceOverviewService raceOverviewService;

    @Autowired
    private RaceOverviewAssembler raceOverviewAssembler;

    @Override
    public ResponseEntity<?> getRaceOverview(@PathVariable long id) {
        RaceOverviewDTO dto = raceOverviewService.getRaceOverview(id);
        return ResponseEntity.ok().body(raceOverviewAssembler.toResource(dto));

    }
}
