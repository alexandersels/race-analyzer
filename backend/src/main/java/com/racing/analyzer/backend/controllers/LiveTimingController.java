package com.racing.analyzer.backend.controllers;

import com.racing.analyzer.backend.assemblers.LiveTimingAssembler;
import com.racing.analyzer.backend.dto.livetiming.LiveTimingDTO;
import com.racing.analyzer.backend.dto.race.RaceDTO;
import com.racing.analyzer.backend.entities.LiveTiming;
import com.racing.analyzer.backend.mappers.LiveTimingMapper;
import com.racing.analyzer.backend.resources.LiveTimingResource;
import com.racing.analyzer.backend.services.LiveTimingService;
import com.racing.analyzer.backend.services.RaceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Resources;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

import static java.util.stream.Collectors.toList;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@RestController
public class LiveTimingController {

    @Autowired
    private LiveTimingService timingService;

    @Autowired
    private RaceService raceService;

    @Autowired
    private LiveTimingAssembler assembler;

    @Autowired
    private LiveTimingMapper mapper;

    @GetMapping("/livetimings/{id}")
    public ResponseEntity<?> getById(@PathVariable long id) {
        Optional<LiveTiming> liveTiming = timingService.getById(id);
        if (!liveTiming.isPresent()) {
            return ResponseEntity.notFound().build();
        } else {
            LiveTimingDTO dto = mapper.toDto(liveTiming.get());
            return ResponseEntity.ok().body(assembler.toResource(dto));
        }
    }

    @GetMapping("/livetimings")
    public ResponseEntity<?> getTimings() {
        Collection<LiveTiming> liveTimings = timingService.getAll();
        List<LiveTimingResource> timingResources = liveTimings.stream().map(timing -> mapper.toDto(timing))
                                                              .map(dto -> assembler.toResource(dto))
                                                              .collect(toList());

        Resources resources = new Resources<>(timingResources);
        resources.add(linkTo(methodOn(LiveTimingController.class).getTimings()).withSelfRel());
        return ResponseEntity.ok().body(resources);
    }

    @GetMapping("/races/{id}/livetimings")
    public ResponseEntity<?> getTimingsForRace(@PathVariable long id) {
        Collection<LiveTimingDTO> timings = timingService.getTimingsForRace(id);
        Optional<RaceDTO> race = raceService.getById(id);
        if (!race.isPresent()) {
            return ResponseEntity.notFound().build();
        }

        Collection<LiveTimingResource> timingResources = timings.stream()
                                                                .map(dto -> assembler.toResource(dto))
                                                                .collect(toList());
        Resources resources = new Resources(timingResources);
        resources.add(linkTo(methodOn(LiveTimingController.class).getTimings()).withRel("allTimings"));
        return ResponseEntity.ok().body(resources);
    }

    @GetMapping("/races/{id}/livetimings/driver/{number}")
    public ResponseEntity<?> getTimingsForRaceAndDriver(@PathVariable long id, @PathVariable int number) {
        Collection<LiveTimingDTO> timingsForDriver = timingService.getTimingsForDriver(id, number);
        List<LiveTimingResource> timingResources = timingsForDriver.stream()
                                                                   .map(dto -> assembler.toResource(dto))
                                                                   .collect(toList());

        Resources resources = new Resources(timingResources);
        resources.add(linkTo(methodOn(LiveTimingController.class).getTimingsForRace(id)).withSelfRel());
        resources.add(linkTo(methodOn(LiveTimingController.class).getTimings()).withRel("allTimings"));
        return ResponseEntity.ok().body(resources);
    }
}
