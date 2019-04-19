package com.racing.analyzer.backend.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public interface RaceOverviewController {
    @GetMapping("/races/{id}/overview")
    ResponseEntity<?> getRaceOverview(@PathVariable long id);
}
