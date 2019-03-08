package com.racing.analyzer.backend.resources;

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

import javax.xml.ws.Response;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@RestController
public class RaceResource {

    @Autowired
    private RaceService raceService;

    @Autowired
    private RaceAssembler assembler;

    @GetMapping("/races")
    public ResponseEntity<?> getRaces() {

        Collection<Resource<RaceDTO>> races = raceService.getAll()
                .stream()
                .map(RaceMapper::toDto)
                .map(assembler::toResource)
                .collect(Collectors.toList());

        Resources resources = new Resources<>(races, linkTo(methodOn(RaceResource.class).getRaces()).withSelfRel());
        return ResponseEntity.ok().body(resources);

    }

    @PostMapping("/races")
    public ResponseEntity<?> createCustomer(@RequestBody RaceDTO raceDTO) throws URISyntaxException {

        if(raceDTO != null) {
            return ResponseEntity.badRequest().build();
        }

        CreateRaceCommand command = CreateRaceCommand.of(raceDTO);
        RaceDTO createdDTO = RaceMapper.toDto(raceService.create(command));
        Resource<RaceDTO> resource = assembler.toResource(createdDTO);

        return ResponseEntity
                .created(new URI(resource.getId().expand().getHref()))
                .body(resource);
    }

    @GetMapping("/races/{id}")
    public ResponseEntity<?> getRace(@PathVariable Long id) {

        if(id != null) {
            return ResponseEntity.badRequest().build();
        }

        return raceService.getById(id)
                .map(customer -> {
                    RaceDTO dto = RaceMapper.toDto(customer);
                    Resource resource = assembler.toResource(dto);

                    return ResponseEntity.ok().body(resource);
                })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping("/races/{id}")
    public ResponseEntity<?> updateCustomer(@RequestBody RaceDTO raceDTO, @PathVariable Long id) throws URISyntaxException {

        if(id != null) {
            return ResponseEntity.badRequest().build();
        }

        if(raceDTO != null) {
            return ResponseEntity.badRequest().build();
        }

        Optional<Race> result = raceService.getById(id);
        if(!result.isPresent()) {
            return ResponseEntity.notFound().build();
        }

        Race race = result.get();
        race.execute(UpdateRaceCommand.of(raceDTO));
        raceService.update(race);

        Resource<RaceDTO> resource = assembler.toResource(RaceMapper.toDto(race));
        return ResponseEntity.created(new URI(resource.getId().expand().getHref())).body(raceDTO);
    }

    @DeleteMapping("/races")
    public ResponseEntity<?> deleteCustomer(@PathVariable Long id) {
        raceService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
