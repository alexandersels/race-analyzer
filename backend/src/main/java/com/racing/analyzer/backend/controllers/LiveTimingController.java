package com.racing.analyzer.backend.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public interface LiveTimingController {
    @GetMapping("/livetimings/{id}")
    ResponseEntity<?> getById(@PathVariable long id);

    @GetMapping("/livetimings")
    ResponseEntity<?> getTimings();

    @GetMapping("/races/{id}/livetimings")
    ResponseEntity<?> getTimingsForRace(@PathVariable long id);

    @GetMapping("/races/{id}/livetimings/driver/{number}")
    ResponseEntity<?> getTimingsForRaceAndDriver(@PathVariable long id, @PathVariable int number);
}
