package com.racing.analyzer.backend.assemblers;

import com.racing.analyzer.backend.controllers.impl.LiveTimingControllerImpl;
import com.racing.analyzer.backend.controllers.impl.RaceControllerImp;
import com.racing.analyzer.backend.controllers.impl.RaceOverviewControllerImpl;
import com.racing.analyzer.backend.dto.statistics.RaceOverviewDTO;
import com.racing.analyzer.backend.resources.RaceOverviewResource;
import org.springframework.hateoas.ResourceAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@Component
public class RaceOverviewAssembler implements ResourceAssembler<RaceOverviewDTO, RaceOverviewResource> {

    @Override
    public RaceOverviewResource toResource(RaceOverviewDTO raceOverviewDTO) {
        RaceOverviewResource resource = RaceOverviewResource.fromDto(raceOverviewDTO);
        resource.add(linkTo(methodOn(RaceControllerImp.class).
                getById(raceOverviewDTO.getId()))
                .withRel("race"));
        resource.add(linkTo(methodOn(LiveTimingControllerImpl.class).
                getTimingsForRace(raceOverviewDTO.getId()))
                .withRel("timings"));
        resource.add(linkTo(methodOn(RaceOverviewControllerImpl.class)
                .getRaceOverview(raceOverviewDTO.getId()))
                .withSelfRel());
        return resource;
    }
}

