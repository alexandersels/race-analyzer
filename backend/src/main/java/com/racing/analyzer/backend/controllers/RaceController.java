package com.racing.analyzer.backend.controllers;

import com.racing.analyzer.backend.assemblers.RaceAssembler;
import com.racing.analyzer.backend.dto.race.CreateRaceDTO;
import com.racing.analyzer.backend.dto.race.RaceDTO;
import com.racing.analyzer.backend.dto.race.UpdateRaceDTO;
import com.racing.analyzer.backend.resources.RaceResource;
import com.racing.analyzer.backend.services.RaceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Resources;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.net.URISyntaxException;

import static java.util.stream.Collectors.toList;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@RestController
public class RaceController {

    @Autowired
    private RaceService raceService;

    @Autowired
    private RaceAssembler raceAssembler;

    @GetMapping("/races/{id}")
    public ResponseEntity<?> getById(@PathVariable long id) {
        RaceDTO race = raceService.getById(id);
        return ResponseEntity.ok().body(raceAssembler.toResource(race));
    }

    @GetMapping("/races")
    public ResponseEntity<?> getRaces() {
        Resources<RaceResource> resources = new Resources<>(raceService.getAll().stream()
                                                                       .map(raceAssembler::toResource)
                                                                       .collect(toList()));

        resources.add(linkTo(methodOn(RaceController.class).getRaces()).withSelfRel());
        return ResponseEntity.ok().body(resources);
    }

    @GetMapping("/races/recording")
    public ResponseEntity<?> getRecordingRaces() {
        Resources<RaceResource> resources = new Resources<>(raceService.getRecordingRaces().stream()
                                                                       .map(raceAssembler::toResource)
                                                                       .collect(toList()));

        resources.add(linkTo(methodOn(RaceController.class).getRecordingRaces()).withSelfRel());
        return ResponseEntity.ok().body(resources);
    }

    @PostMapping("/races")
    public ResponseEntity<?> create(@RequestBody CreateRaceDTO createRaceDTO) throws URISyntaxException {
        if (createRaceDTO == null) {
            return ResponseEntity.badRequest().build();
        }

        RaceDTO created = raceService.create(createRaceDTO);
        RaceResource resource = raceAssembler.toResource(created);
        return ResponseEntity
                .created(new URI(resource.getId().expand().getHref()))
                .body(resource);
    }

    @PutMapping("/races/")
    public ResponseEntity<?> update(@RequestBody UpdateRaceDTO updateRaceDTO) throws URISyntaxException {
        if (updateRaceDTO == null) {
            return ResponseEntity.badRequest().build();
        }

        RaceDTO update = raceService.update(updateRaceDTO);
        RaceResource resource = raceAssembler.toResource(update);
        return ResponseEntity
                .created(new URI(resource.getId().expand().getHref()))
                .body(resource);
    }

    @PutMapping("/races/{id}/start-recording")
    public ResponseEntity<?> startRecording(@PathVariable long id) {
        return changeRecordingState(id, true);
    }

    @PutMapping("/races/{id}/stop-recording")
    public ResponseEntity<?> stopRecording(@PathVariable long id) {
        return changeRecordingState(id, false);
    }

    @DeleteMapping("/races")
    public ResponseEntity<?> delete(@PathVariable long id) {
        raceService.delete(id);
        return ResponseEntity.noContent().build();
    }

    private ResponseEntity<?> changeRecordingState(long id, boolean isRecording) {
        RaceDTO updated = raceService.changeRecordingState(id, isRecording);
        RaceResource resource = raceAssembler.toResource(updated);
        return ResponseEntity
                .created(URI.create(resource.getId().expand().getHref()))
                .body(resource);
    }
}
