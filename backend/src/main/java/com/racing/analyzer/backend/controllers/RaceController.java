package com.racing.analyzer.backend.controllers;

import com.racing.analyzer.backend.dto.race.CreateRaceDTO;
import com.racing.analyzer.backend.dto.race.UpdateRaceDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.net.URISyntaxException;

@RestController
public interface RaceController {
    @GetMapping("/races/{id}")
    ResponseEntity<?> getById(@PathVariable long id);

    @GetMapping("/races")
    ResponseEntity<?> getRaces();

    @PostMapping("/races")
    ResponseEntity<?> create(@RequestBody CreateRaceDTO createRaceDTO) throws URISyntaxException;

    @PutMapping("/races/")
    ResponseEntity<?> update(@RequestBody UpdateRaceDTO updateRaceDTO) throws URISyntaxException;

    @PutMapping("/races/{id}/start-recording")
    ResponseEntity<?> startRecording(@PathVariable long id);

    @PutMapping("/races/{id}/stop-recording")
    ResponseEntity<?> stopRecording(@PathVariable long id);

    @DeleteMapping("/races")
    ResponseEntity<?> delete(@PathVariable long id);
}
