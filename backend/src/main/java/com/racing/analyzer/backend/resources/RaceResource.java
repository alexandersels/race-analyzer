package com.racing.analyzer.backend.resources;

import com.racing.analyzer.backend.assemblers.RaceAssembler;
import com.racing.analyzer.backend.commands.race.CreateRaceCommand;
import com.racing.analyzer.backend.commands.race.UpdateRaceCommand;
import com.racing.analyzer.backend.dto.race.CreateRaceDTO;
import com.racing.analyzer.backend.dto.race.RaceDTO;
import com.racing.analyzer.backend.dto.race.UpdateRaceDTO;
import com.racing.analyzer.backend.services.RaceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.Resources;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.Optional;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@RestController
public class RaceResource extends BaseResource {

    @Autowired
    private RaceService service;

    @Autowired
    private RaceAssembler assembler;

    @GetMapping("/races/{id}")
    public ResponseEntity<?> getById(@PathVariable long id) {
        Optional<RaceDTO> race = service.getById(id);
        return race.map(dto -> {
            Resource resource = assembler.toResource(dto);
            return ResponseEntity.ok().body(resource);
        }).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/races")
    public ResponseEntity<?> getRaces() {
        Collection<RaceDTO> races = service.getAll();
        Resources resources = new Resources<>(races, linkTo(methodOn(RaceResource.class).getRaces()).withSelfRel());
        return ResponseEntity.ok().body(resources);
    }

    @PostMapping("/races")
    public ResponseEntity<?> create(@RequestBody CreateRaceDTO createRaceDTO) {

        if (createRaceDTO == null) {
            return ResponseEntity.badRequest().build();
        }

        CreateRaceCommand command = CreateRaceCommand.fromDTO(createRaceDTO);
        Optional<RaceDTO> created = service.create(command);
        if (created.isPresent()) {
            Resource resource = assembler.toResource(created.get());
            return ResponseEntity.created(createUri(resource)).body(resource);
        } else {
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("/races/")
    public ResponseEntity<?> update(@RequestBody UpdateRaceDTO updateRaceDTO) {

        if (updateRaceDTO == null) {
            return ResponseEntity.badRequest().build();
        }

        UpdateRaceCommand command = UpdateRaceCommand.fromDto(updateRaceDTO);
        Optional<RaceDTO> update = service.update(command);
        if (update.isPresent()) {
            Resource resource = assembler.toResource(update.get());
            return ResponseEntity.created(createUri(resource)).body(resource);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/races")
    public ResponseEntity<?> deleteCustomer(@PathVariable long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
