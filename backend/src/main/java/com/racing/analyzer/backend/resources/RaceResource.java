package com.racing.analyzer.backend.resources;

import com.racing.analyzer.backend.ScheduledTask;
import com.racing.analyzer.backend.assemblers.RaceAssembler;
import com.racing.analyzer.backend.commands.CreateRaceCommand;
import com.racing.analyzer.backend.commands.UpdateRaceCommand;
import com.racing.analyzer.backend.dto.RaceDTO;
import com.racing.analyzer.backend.entities.Race;
import com.racing.analyzer.backend.mappers.RaceMapper;
import com.racing.analyzer.backend.services.RaceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.Resources;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@RestController
public class RaceResource extends BaseResource {

    @Autowired
    private RaceService service;

    @Autowired
    private RaceAssembler assembler;

    @Autowired
    private ScheduledTask task;

    @GetMapping("/races")
    public ResponseEntity<?> getRaces() {

        Collection<Resource<RaceDTO>> races = service.getAll()
                .stream()
                .map(RaceMapper::toDto)
                .map(assembler::toResource)
                .collect(Collectors.toList());

        Resources resources = new Resources<>(races, linkTo(methodOn(RaceResource.class).getRaces()).withSelfRel());
        return ResponseEntity.ok().body(resources);

    }

    @GetMapping("/races/{id}")
    public ResponseEntity<?> getById(@PathVariable Long id) {

        if (id == null) {
            return ResponseEntity.badRequest().build();
        }

        return service.getById(id)
                .map(customer -> {
                    RaceDTO dto = RaceMapper.toDto(customer);
                    Resource resource = assembler.toResource(dto);

                    return ResponseEntity.ok().body(resource);
                })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping("/races")
    public ResponseEntity<?> create(@RequestBody RaceDTO raceDTO) throws URISyntaxException {

        if (raceDTO != null) {
            return ResponseEntity.badRequest().build();
        }

        CreateRaceCommand command = CreateRaceCommand
                .getBuilder()
                .startFrom(raceDTO)
                .build();
        RaceDTO createdDTO = RaceMapper.toDto(service.create(command));
        Resource<RaceDTO> resource = assembler.toResource(createdDTO);

        return ResponseEntity
                .created(new URI(resource.getId().expand().getHref()))
                .body(resource);
    }

    @PutMapping("/races/{id}")
    public ResponseEntity<?> update(@RequestBody RaceDTO raceDTO, @PathVariable Long id) throws URISyntaxException {

        if (id != null) {
            return ResponseEntity.badRequest().build();
        }

        if (raceDTO != null) {
            return ResponseEntity.badRequest().build();
        }

        Optional<Race> result = service.getById(id);
        if (!result.isPresent()) {
            return ResponseEntity.notFound().build();
        }

        Race race = result.get();
        race.execute(UpdateRaceCommand.getBuilder().startFrom(raceDTO).build());
        service.update(race);

        Resource<RaceDTO> resource = assembler.toResource(RaceMapper.toDto(race));
        return ResponseEntity.created(new URI(resource.getId().expand().getHref())).body(raceDTO);
    }

    @PutMapping("/races/{id}/record/{shouldRecord}")
    public ResponseEntity<?> switchRecordState(@PathVariable Long id, @PathVariable boolean shouldRecord) {
        return service.getById(id)
                .map(race -> {
                    if (shouldRecord) {
                        service.stopAllRecordings();
                    }
                    UpdateRaceCommand updateRaceCommand = UpdateRaceCommand.getBuilder()
                            .startFrom(race)
                            .isRecording(shouldRecord)
                            .build();
                    race.execute(updateRaceCommand);
                    service.update(race);
                    task.recordingFor(shouldRecord, race);

                    RaceDTO updated = RaceMapper.toDto(race);
                    Resource<RaceDTO> resource = assembler.toResource(updated);

                    return ResponseEntity
                            .created(createUri(resource))
                            .body(updated);

                })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/races")
    public ResponseEntity<?> deleteCustomer(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
