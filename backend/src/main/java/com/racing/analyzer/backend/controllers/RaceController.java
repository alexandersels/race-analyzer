package com.racing.analyzer.backend.controllers;

import com.racing.analyzer.backend.assemblers.RaceAssembler;
import com.racing.analyzer.backend.commands.race.CreateRaceCommand;
import com.racing.analyzer.backend.commands.race.UpdateRaceCommand;
import com.racing.analyzer.backend.dto.race.CreateRaceDTO;
import com.racing.analyzer.backend.dto.race.RaceDTO;
import com.racing.analyzer.backend.dto.race.UpdateRaceDTO;
import com.racing.analyzer.backend.entities.Race;
import com.racing.analyzer.backend.mappers.RaceMapper;
import com.racing.analyzer.backend.resources.RaceResource;
import com.racing.analyzer.backend.services.RaceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Resources;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Collection;
import java.util.Optional;

import static java.util.stream.Collectors.toList;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@RestController
public class RaceController {

    @Autowired
    private RaceService service;

    @Autowired
    private RaceAssembler raceAssembler;

    @Autowired
    private RaceMapper mapper;

    @GetMapping("/races/{id}")
    public ResponseEntity<?> getById(@PathVariable long id) {
        Optional<Race> race = service.getById(id);
        if (!race.isPresent()) {
            return ResponseEntity.notFound().build();
        } else {
            RaceDTO dto = mapper.toDto(race.get());
            return ResponseEntity.ok().body(raceAssembler.toResource(dto));
        }
    }

    @GetMapping("/races")
    public ResponseEntity<?> getRaces() {
        Collection<RaceResource> raceResources = service.getAll().stream()
                .map(race -> mapper.toDto(race))
                .map(dto -> raceAssembler.toResource(dto))
                .collect(toList());

        Resources resources = new Resources(raceResources);
        resources.add(linkTo(methodOn(RaceController.class).getRaces()).withSelfRel());

        return ResponseEntity.ok().body(resources);
    }

    @PostMapping("/races")
    public ResponseEntity<?> create(@RequestBody CreateRaceDTO createRaceDTO) throws URISyntaxException {
        if (createRaceDTO == null) {
            return ResponseEntity.badRequest().build();
        }

        Optional<Race> created = service.create(CreateRaceCommand.fromDTO(createRaceDTO));
        if (created.isPresent()) {
            RaceResource resource = raceAssembler.toResource(mapper.toDto(created.get()));
            return ResponseEntity
                    .created(new URI(resource.getId().expand().getHref()))
                    .body(resource);
        } else {
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("/races/")
    public ResponseEntity<?> update(@RequestBody UpdateRaceDTO updateRaceDTO) throws URISyntaxException {
        if (updateRaceDTO == null) {
            return ResponseEntity.badRequest().build();
        }

        Optional<Race> update = service.update(UpdateRaceCommand.fromDto(updateRaceDTO));
        if (update.isPresent()) {
            RaceResource resource = raceAssembler.toResource(mapper.toDto(update.get()));
            return ResponseEntity
                    .created(new URI(resource.getId().expand().getHref()))
                    .body(resource);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/races")
    public ResponseEntity<?> delete(@PathVariable long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
