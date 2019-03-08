package com.racing.analyzer.backend.assemblers;

import com.racing.analyzer.backend.dto.RaceDTO;
import com.racing.analyzer.backend.resources.RaceResource;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.ResourceAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@Component
public class RaceAssembler implements ResourceAssembler<RaceDTO, Resource<RaceDTO>> {

    @Override
    public Resource<RaceDTO> toResource(RaceDTO raceDTO) {

        return new Resource<>(raceDTO,
                linkTo(methodOn(RaceResource.class).getRace(raceDTO.id)).withSelfRel(),
                linkTo(methodOn(RaceResource.class).getRaces()).withRel("races"));

    }
}

