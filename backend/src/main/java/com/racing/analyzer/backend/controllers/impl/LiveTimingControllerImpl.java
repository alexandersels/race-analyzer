package com.racing.analyzer.backend.controllers.impl;

import com.racing.analyzer.backend.assemblers.LiveTimingAssembler;
import com.racing.analyzer.backend.dto.livetiming.LiveTimingDTO;
import com.racing.analyzer.backend.resources.LiveTimingResource;
import com.racing.analyzer.backend.services.LiveTimingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Resources;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;
import java.util.List;

import static java.util.stream.Collectors.toList;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@Component
public class LiveTimingControllerImpl implements com.racing.analyzer.backend.controllers.LiveTimingController {

    @Autowired
    private LiveTimingService liveTimingService;

    @Autowired
    private LiveTimingAssembler liveTimingAssembler;

    @Override
    public ResponseEntity<?> getById(@PathVariable long id) {
        LiveTimingDTO dto = liveTimingService.getById(id);
        return ResponseEntity.ok().body(liveTimingAssembler.toResource(dto));
    }

    @Override
    public ResponseEntity<?> getTimings() {
        List<LiveTimingResource> timingResources = liveTimingService.getAll().stream()
                .map(dto -> liveTimingAssembler.toResource(dto))
                .collect(toList());

        Resources resources = new Resources<>(timingResources);
        resources.add(linkTo(methodOn(LiveTimingControllerImpl.class).getTimings()).withSelfRel());
        return ResponseEntity.ok().body(resources);
    }

    @Override
    public ResponseEntity<?> getTimingsForRace(@PathVariable long id) {
        Collection<LiveTimingResource> timings = liveTimingService.getTimingsForRace(id).stream()
                .map(dto -> liveTimingAssembler.toResource(dto))
                .collect(toList());
        Resources resources = new Resources<>(timings);
        resources.add(linkTo(methodOn(LiveTimingControllerImpl.class).getTimings()).withRel("allTimings"));
        return ResponseEntity.ok().body(resources);
    }

    @Override
    public ResponseEntity<?> getTimingsForRaceAndDriver(@PathVariable long id, @PathVariable int number) {
        Collection<LiveTimingDTO> timingsForDriver = liveTimingService.getTimingsForDriver(id, number);
        List<LiveTimingResource> timingResources = timingsForDriver.stream()
                .map(dto -> liveTimingAssembler.toResource(dto))
                .collect(toList());

        Resources resources = new Resources<>(timingResources);
        resources.add(linkTo(methodOn(LiveTimingControllerImpl.class).getTimingsForRace(id)).withSelfRel());
        resources.add(linkTo(methodOn(LiveTimingControllerImpl.class).getTimings()).withRel("allTimings"));
        return ResponseEntity.ok().body(resources);
    }
}
