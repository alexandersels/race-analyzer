package com.racing.analyzer.backend.assemblers;

import com.racing.analyzer.backend.controllers.RaceController;
import com.racing.analyzer.backend.controllers.RaceOverviewController;
import com.racing.analyzer.backend.dto.race.RaceDTO;
import com.racing.analyzer.backend.entities.Race;
import com.racing.analyzer.backend.resources.RaceResource;
import org.springframework.hateoas.ResourceAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@Component
public class RaceAssembler implements ResourceAssembler<RaceDTO, RaceResource> {

    @Override
    public RaceResource toResource(RaceDTO raceDTO) {
        RaceResource resource = RaceResource.fromDto(raceDTO);
        resource.add(linkTo(methodOn(RaceController.class)
                .getById(raceDTO.getId()))
                .withSelfRel());
        resource.add(linkTo(methodOn(RaceController.class)
                .getRaces())
                .withRel("allRaces"));
        resource.add(linkTo(methodOn(RaceOverviewController.class)
                .getRaceOverview(raceDTO.getId()))
                .withRel("statistics"));
        return resource;
    }
}

