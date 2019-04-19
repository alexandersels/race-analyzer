package com.racing.analyzer.backend.controllers.impl;

import com.racing.analyzer.backend.assemblers.RaceAssembler;
import com.racing.analyzer.backend.dto.race.CreateRaceDTO;
import com.racing.analyzer.backend.dto.race.RaceDTO;
import com.racing.analyzer.backend.dto.race.UpdateRaceDTO;
import com.racing.analyzer.backend.resources.RaceResource;
import com.racing.analyzer.backend.services.RaceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Resources;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import static java.util.stream.Collectors.toList;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@Component
public class RaceControllerImp implements com.racing.analyzer.backend.controllers.RaceController {

    @Autowired
    private RaceService raceService;

    @Autowired
    private RaceAssembler raceAssembler;

    @Override
    public ResponseEntity<?> getById(@PathVariable long id) {
        RaceDTO race = raceService.getById(id);
        return ResponseEntity.ok().body(raceAssembler.toResource(race));
    }

    @Override
    public ResponseEntity<?> getRaces() {
        Resources<RaceResource> resources = new Resources<>(raceService.getAll().stream()
                .map(race -> raceAssembler.toResource(race))
                .collect(toList()));

        resources.add(linkTo(methodOn(RaceControllerImp.class).getRaces()).withSelfRel());
        return ResponseEntity.ok().body(resources);
    }

    @Override
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

    @Override
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

    @Override
    public ResponseEntity<?> startRecording(@PathVariable long id) {
        return changeRecordingState(id, true);
    }

    @Override
    public ResponseEntity<?> stopRecording(@PathVariable long id) {
        return changeRecordingState(id, false);
    }

    @Override
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
