package com.racing.analyzer.backend.assemblers;

import com.racing.analyzer.backend.controllers.impl.RaceControllerImp;
import com.racing.analyzer.backend.controllers.impl.RaceOverviewControllerImpl;
import com.racing.analyzer.backend.dto.race.RaceDTO;
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
        resource.add(linkTo(methodOn(RaceControllerImp.class)
                .getById(raceDTO.getId()))
                .withSelfRel());
        resource.add(linkTo(methodOn(RaceControllerImp.class)
                .getRaces())
                .withRel("allRaces"));
        resource.add(linkTo(methodOn(RaceControllerImp.class)
                .startRecording(raceDTO.getId()))
                .withRel("start-recording"));
        resource.add(linkTo(methodOn(RaceControllerImp.class)
                .stopRecording(raceDTO.getId()))
                .withRel("stop-recording"));
        resource.add(linkTo(methodOn(RaceOverviewControllerImpl.class)
                .getRaceOverview(raceDTO.getId()))
                .withRel("statistics"));
        return resource;
    }
}

